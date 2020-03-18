package ru.geekbrains.java2.server.client;

import ru.geekbrains.java2.server.NetworkServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final NetworkServer networkServer;
    private final Socket clientSocket;
    private  DataInputStream in;
    private  DataOutputStream out;

    private String nickName;
    //private String login;

    public String getNickName() {
        return nickName;
    }

//    public String getLogin() {
//        return login;
//    }

    public ClientHandler(NetworkServer networkServer, Socket socket)  {
        this.networkServer = networkServer;
        this.clientSocket = socket;
    }

    public void run(){
        doHandle(clientSocket);
    }

    private void doHandle(Socket socket) {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    authentication();
                    readMessages();
                }catch (IOException e){
                    System.out.println("Соединение с клиентом " + nickName + " было закрыто!");
                } finally {
                    closeConnection();
                }
            }).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        networkServer.unsubscribe(this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true){
                String message = in.readUTF();
                System.out.printf("От %s : %s%n", nickName, message);
                if("/end".equals(message)){
                    return;
                }

                networkServer.broadcastMessage(message, this);
        }
    }

    private void authentication() throws IOException {

            while (true){
                String message =  in.readUTF();
                if(message.startsWith("/auth")){
                    String[] messageParts = message.split("\\s+", 3);
                    String userlogin = messageParts[1];
                    String password = messageParts[2];
                    String userName = networkServer.getAuthService().getUserNameByLoginAndPassword(userlogin, password);

                    if(userName==null){
                        sendMessage("Неверный логин и пароль");
                    }else {
                        nickName = userName;
                        //login = userlogin;
                        networkServer.broadcastMessage(nickName + " зашел в чат", this);
                        sendMessage("/auth " + nickName);//отправим клиенту, что он авторизовался
                        networkServer.subscribe(this);//добавим в список авторизованных
                        break;
                    }

            }}

    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }
}
