package lesson6.homeWork_6;

import java.io.*;

import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private final static String CLOSE_WORD = "/end";
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8180;

    public static void main(String[] args) {

        try{
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            Thread clientAnswer = new Thread(ChatClient::outWriteUTF);//исходящие сообщения
            Thread serverAsk = new Thread(ChatClient::inReadUTF);//входящие сообщения

            serverAsk.start();
            clientAnswer.start();

            try {
                serverAsk.join();
                clientAnswer.join();
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }

    }

    public static void inReadUTF(){
        try{
            in = new DataInputStream(socket.getInputStream());
            while(true){
                if(socket==null || socket.isClosed() || in == null) break;
                String str = in.readUTF();
                if (str.trim().isEmpty()) continue;
                System.out.printf("Сервер: %s %n",str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void outWriteUTF(){
        Scanner sc = new Scanner(System.in);

        try{
            out = new DataOutputStream(socket.getOutputStream());
            while(true){
                String str = sc.nextLine();
                out.writeUTF(str);
                if(str.equals(CLOSE_WORD)){
                    closeConnection();
                    System.out.println("Ожидание ввода завершено");
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            sc.close();
        }
    }

    public static void closeConnection(){
        try{
            in.close();
            out.close();
            if(socket!=null) socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
