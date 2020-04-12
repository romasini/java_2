package ru.geekbrains.java2.server.client;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.CommandType;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.BroadcastMessageCommand;
import ru.geekbrains.java2.client.command.ChangeNicknameCommand;
import ru.geekbrains.java2.client.command.PrivateMessageCommand;
import ru.geekbrains.java2.server.NetworkServer;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private final NetworkServer networkServer;
    private final Socket clientSocket;
    private static final int TIMEOUT = 30;
    private static final String MASK_WORD = "***";

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String nickname;

    public ClientHandler(NetworkServer networkServer, Socket socket)  {
        this.networkServer = networkServer;
        this.clientSocket = socket;
    }

    public String getNickname() {
        return nickname;
    }

    public void run(){
        doHandle(clientSocket);
    }

    private void doHandle(Socket socket) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            executorService.execute(()->{
                try {
                    authentication();
                    readMessages();
                }catch (IOException e){
                    //System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                    LOGGER.log(Level.WARNING, "Соединение с клиентом " + nickname + " было закрыто!");
                } finally {
                    closeConnection();
                }
            });

            executorService.execute(()->{
                try {
                    closeByTimeout();
                } catch (InterruptedException e) {
                    //System.out.println("Ошибка с отсчетом таймаута");
                    LOGGER.log(Level.SEVERE, "Ошибка с отсчетом таймаута", e);
                } catch (IOException e) {
                    //System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                    LOGGER.log(Level.WARNING, "Соединение с клиентом " + nickname + " было закрыто!");
                }
            });

            /*
            new Thread(()->{
                try {
                    authentication();
                    readMessages();
                }catch (IOException e){
                    System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                } finally {
                    closeConnection();
                }
            }).start();

            new Thread(()->{
                try {
                    closeByTimeout();
                } catch (InterruptedException e) {
                    System.out.println("Ошибка с отсчетом таймаута");
                } catch (IOException e) {
                    System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                }
            }).start();*/

            executorService.shutdown();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            networkServer.unsubscribe(this);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeByTimeout() throws InterruptedException, IOException {
        Thread.currentThread().sleep(TIMEOUT*1000);
        if (nickname == null){
            sendMessage(Command.authErrorCommand("Истекло время ожидания. Соединение закрыто!"));
            closeConnection();
        }
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) in.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Unknown type of object from client!";
//            System.err.println(errorMessage);
//            e.printStackTrace();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private String deleteCursWords(String message){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");
            //statement.setArray(1, connection.createArrayOf("STRING", words));//sqlite не реализует setArray и createArrayOf
            String[] words = message.split("\\s+");
            for (String word: words){
                PreparedStatement statement = connection.prepareStatement("SELECT word FROM curse_words where word = ?;");
                statement.setString(1, word);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    message = message.replaceAll(resultSet.getString("word"), MASK_WORD);
                }
            }

            return message;
        } catch (Exception e) {

//            System.out.println("Ошибка подключения к базе!");
//            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Ошибка подключения к базе!", e);
            return message;

        }finally {
            try {
                if(connection!=null) {connection.close();}
            } catch (SQLException e) {
                //e.printStackTrace();
                LOGGER.log(Level.SEVERE, "Ошибка работы с базой!", e);
            }
        }

    }

    private void readMessages() throws IOException {
        while (true){
            Command command = readCommand();
            if(command == null) continue;

            switch (command.getType()){
                case END:
                    System.out.println("Received 'END' command");
                    break;
                case PRIVATE_MESSAGE:{
                    PrivateMessageCommand commandData = (PrivateMessageCommand) command.getData();
                    String receiver = commandData.getReceiver();
                    String message = commandData.getMessage();
                    message = deleteCursWords(message);
                    networkServer.sendMessage(receiver, Command.messageCommand(nickname, message));
                    break;
                }
                case BROADCAST_MESSAGE:{
                    BroadcastMessageCommand commandData = (BroadcastMessageCommand) command.getData();
                    String message = commandData.getMessage();
                    message = deleteCursWords(message);
                    networkServer.broadcastMessage(Command.messageCommand(nickname, message), this);
                    break;
                }
                case CHANGE_NICKNAME:{
                    ChangeNicknameCommand commandData = (ChangeNicknameCommand) command.getData();
                    String login = commandData.getLogin();
                    String newNickname = commandData.getUsername();
                    if(networkServer.getAuthService().changeNickname(login, newNickname)){
                        sendMessage(command);//отправим клиенту, что всё ОК
                        String message = nickname + " сменил ник на " + newNickname;
                        nickname = newNickname;
                        networkServer.broadcastMessage(Command.messageCommand(null, message), this);//уведомим чат, что ник сменен
                        List<String> users = networkServer.getAllUsernames();
                        networkServer.broadcastMessage(Command.updateUsersListCommand(users), null);//обновим список контактов
                    }else {
                        Command errorCommand = Command.errorCommand("Ошибка при смене ника");
                        sendMessage(errorCommand);
                    }

                    break;
                }
                default:LOGGER.log(Level.SEVERE, "Unknown type of command : " + command.getType());//System.err.println("Unknown type of command : " + command.getType());
            }

            /*String message = in.readUTF();
            System.out.printf("От %s : %s%n", nickName, message);
            if("/end".equals(message)){
                return;
            }

            networkServer.broadcastMessage(message, this);*/
        }
    }

    private void authentication() throws IOException {

            while (true){
                Command command = readCommand();
                if(command==null) continue;
                if(command.getType() == CommandType.AUTH){
                    boolean successfulAuth = processAuthCommand(command);
                    if (successfulAuth){
                        return;
                    }
                }else{
                    //System.err.println("Unknown type of command for auth process: " + command.getType());
                    LOGGER.log(Level.SEVERE,"Unknown type of command for auth process: " + command.getType());
                }
            }

            /*while (true){
                 String message =  in.readUTF();
                if(message.startsWith("/auth")){
                    String[] messageParts = message.split("\\s+", 3);
                    String userlogin = messageParts[1];
                    String password = messageParts[2];
                    String userName = networkServer.getAuthService().getUserNameByLoginAndPassword(userlogin, password);
                    if(userName==null){
                        System.out.println("Отсутствует учетная запись по данному логину и паролю!");
                        sendMessage("Отсутствует учетная запись по данному логину и паролю!");
                    }else {
                        nickName = userName;
                        networkServer.broadcastMessage(" зашел в чат!", this);
                        sendMessage("/auth " + nickname);//отправим клиенту, что он авторизовался
                        networkServer.subscribe(this);//добавим в список авторизованных
                        break;
                    }

            }}*/

    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommand commandData = (AuthCommand) command.getData();
        String login = commandData.getLogin();
        String password = commandData.getPassword();
        String username = networkServer.getAuthService().getUserNameByLoginAndPassword(login, password);
        if(username==null){
            Command authErrorCommand = Command.authErrorCommand("Отсутствует учетная запись по данному логину и паролю!");
            sendMessage(authErrorCommand);
            return false;
        }else if(networkServer.isNicknameBusy(username)){
            Command authErrorCommand = Command.authErrorCommand("Данный пользователь уже авторизован!");
            sendMessage(authErrorCommand);
            return false;
        }else{
            nickname = username;
            String message = nickname + " зашел в чат!";
            networkServer.broadcastMessage(Command.messageCommand(null, message), this);
            //вернем клиенту ответ, что есть никнейм
            commandData.setUsername(nickname);
            sendMessage(command);
            networkServer.subscribe(this);
            return true;
        }

    }

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }
}
