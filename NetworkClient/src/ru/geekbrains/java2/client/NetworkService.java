package ru.geekbrains.java2.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkService {

    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;


    public NetworkService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(){
        try {
            this.socket = new Socket(host, port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  sendAuthMessage(String login, String password) throws IOException {
        out.writeUTF(String.format("/auth %s %s", login, password));
    }
}
