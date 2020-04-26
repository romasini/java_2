package ru.geekbrains.java2.server;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.BaseAuthService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NetworkServer {
    private static final Logger LOGGER = Logger.getLogger(NetworkServer.class.getName());
    private int port;
    /*требуется обеспечить потокобезопасность списка клиентов
    - вариант использовать потокобезопасные коллекции
    - вариант синхронизировать методы, которые осуществляют запись/чтение коллекции
     */
    //private final List<ClientHandler> clients = new ArrayList<>();
    //private final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final AuthService authService;



    public NetworkServer(int port){
        this.port = port;
        this.authService = new BaseAuthService();
    }

    public void start() {

        try(ServerSocket serverSocket = new ServerSocket(port)){
            //System.out.println("Сервер был успешно запущен на порту " + port);
            LOGGER.log(Level.WARNING, "Сервер был успешно запущен на порту " + port);
            authService.start();
            while (true){
                //System.out.println("Ожидание клиентского подключения...");
                LOGGER.log(Level.INFO, "Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                //System.out.println("Клиент подключился...");
                LOGGER.log(Level.WARNING, "Клиент подключился...");
                createClientHandler(clientSocket);
            }
        }catch (IOException e){
            //System.out.println("Ошибка при работе с сервером");
            //e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Ошибка при работе с сервером", e);
        }finally {
            authService.stop();
        }

    }

    private void createClientHandler(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.run();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public /*synchronized*/ void broadcastMessage(Command message, ClientHandler owner) throws IOException {
        //общая отправка
        for (ClientHandler client: clients) {
            if(client!=owner)
                client.sendMessage(message);
        }

        /*
        //адресная отправка
        if(message.startsWith("/w ")){
            String[] messageParts = message.split("\\s+");
            String messageReceiver = messageParts[1];
            ClientHandler clientReceiver = null;
            for (ClientHandler client: clients) {
                if(client.getNickName().equals(messageReceiver)){
                    clientReceiver = client;
                    break;
                }
            }
            if(clientReceiver==null){
                owner.sendMessage("Пользователь не в сети");
            }else {
                String str = message.replaceFirst(messageParts[0] + " " + messageReceiver, "").trim();
                clientReceiver.sendMessage(String.format("%s : %s", owner.getNickName(), str));
            }
            return;
        }*/

    }

    public /*synchronized*/ void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public /*synchronized*/ void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public List<String> getAllUsernames(){
        /*return clients.stream().//формируем стрим
                map(client -> client.getNickname()).//преобразуем список в список никнеймов
                collect(Collectors.toList());//преобразуем в лист обратно*/

        List<String> usernames = new LinkedList<>();
        for (ClientHandler clientHandler : clients) {
            usernames.add(clientHandler.getNickname());
        }
        return usernames;
    }

    public /*synchronized*/ void sendMessage(String receiver, Command commandMessage) throws IOException {
        for(ClientHandler client:clients){
            if(client.getNickname().equals(receiver)){
                client.sendMessage(commandMessage);
            }
        }
    }

    public boolean isNicknameBusy(String username){
        for(ClientHandler client: clients){
            if (client.getNickname().equals(username)){
                return true;
            }
        }
        return  false;
    }

}
