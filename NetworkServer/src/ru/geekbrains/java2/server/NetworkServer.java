package ru.geekbrains.java2.server;

import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.BaseAuthService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkServer {

    private int port;
    private final List<ClientHandler> clients = new ArrayList<>();

    private final AuthService authService;

    public NetworkServer(int port){

        this.port = port;
        this.authService = new BaseAuthService();
    }

    public void start() {

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен на порту " + port);
            authService.start();
            while (true){
                System.out.println("Ждем клиента");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился...");
                clients.add(new ClientHandler(this, clientSocket));
            }
        }catch (IOException e){
            System.out.println("Ошибка при работе с сервером");
            e.printStackTrace();
        }finally {
            authService.stop();
        }

    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void broadcastMessage(String message) throws IOException {
        for (ClientHandler client: clients) {
                client.sendMessage(message);
        }
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
