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

    public ClientHandler(NetworkServer networkServer, Socket socket)  {

        this.networkServer = networkServer;
        this.clientSocket = socket;

        doHandle(socket);


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
                    System.out.println("Ошибка соединения");
                } finally {
                    closeConnection();
                }
            });
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
                networkServer.broadcastMessage(nickName + ": "+ message);
        }
    }

    private void authentication() throws IOException {

            while (true){
                String message =  in.readUTF();
                if(message.startsWith("/auth")){
                    String[] messageParts = message.split("\\s+", 3);
                    String login = messageParts[1];
                    String password = messageParts[2];
                    String userName = networkServer.getAuthService().getUserNameByLoginAndPassword(login, password);

                    if(userName==null){
                        sendMessage("Неверный логин и пароль");
                    }else {
                        nickName = userName;
                        networkServer.broadcastMessage(nickName + " зашел в чат");
                        break;
                    }

            }}

    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }
}
