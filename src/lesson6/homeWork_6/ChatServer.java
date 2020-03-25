package lesson6.homeWork_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

    private static Socket clientSocket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private final static String CLOSE_WORD = "/end";
    private final static int SERVER_PORT = 8180;

    public static void main(String[] args)  {

        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){//serverSocket Закроется сам
            System.out.println("Сервер запущен, ожидает подключение ...");
            clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился ... ");

            Thread clientAsk = new Thread(ChatServer::inReadUTF);//входящие сообщения
            Thread serverAnswer = new Thread(ChatServer::outWriteUTF);//исходящие сообщения
            clientAsk.start();
            serverAnswer.start();

            try {
                clientAsk.join();
                serverAnswer.join();
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(clientSocket!=null) clientSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void inReadUTF(){
        try{
            in = new DataInputStream(clientSocket.getInputStream());
            while(true){
                String str = in.readUTF();
                if (str.trim().isEmpty()) continue;
                if (str.equals(CLOSE_WORD)){
                    System.out.println("Клиент отключился");
                    closeConnection();
                    break;
                }

                System.out.printf("Клиент: %s %n",str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void outWriteUTF(){
        Scanner sc = new Scanner(System.in);

        try{
            out = new DataOutputStream(clientSocket.getOutputStream());
            while(true){
                if(out==null||clientSocket==null||clientSocket.isClosed()) break;
                String str = sc.nextLine();
                out.writeUTF(str);
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
            if(clientSocket!=null) clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
