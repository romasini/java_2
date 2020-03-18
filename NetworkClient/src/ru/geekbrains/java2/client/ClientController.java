package ru.geekbrains.java2.client;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.authform.ClientAuthController;
import ru.geekbrains.java2.client.chatform.ClientChatController;

import java.io.IOException;

public class ClientController {

    private final NetworkService networkService;
    private Stage primaryStage;
    private Parent rootChat;
    private String nickname;

    public ClientController(String serverHost, int serverPort, Stage primaryStage) throws IOException {

        this.networkService = new NetworkService(serverHost, serverPort);
        this.primaryStage = primaryStage;

    }

    public void runApplication() throws IOException {
        openAuth();
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() throws IOException {
        networkService.setSuccessfulAuthEvent(nickname -> {
            setUserName(nickname);
            Platform.runLater(()->{
                try {
                    openChat();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    private void openAuth() throws IOException {
        FXMLLoader loaderAuth = new FXMLLoader();
        rootChat = loaderAuth.load(getClass().getResourceAsStream("authform/ClientAuthForm.fxml"));
        ClientAuthController authDialog  = loaderAuth.getController();
        authDialog.setController(this);

        Scene scene = new Scene(rootChat, 500, 230);
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.exit(0);
        });
    }

    private void openChat() throws IOException {
        System.out.println("2 " + Thread.currentThread().getName());
        FXMLLoader loaderChat = new FXMLLoader();
        rootChat = loaderChat.load(getClass().getResourceAsStream("chatform/ClientChatForm.fxml"));
        ClientChatController clientChat  = loaderChat.getController();
        clientChat.setController(this);

        Scene scene = new Scene(rootChat, 600, 400);
        System.out.println(nickname);
        primaryStage.setTitle("Супер чат :" + nickname);
        primaryStage.setScene(scene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.exit(0);
        });

        networkService.setMessageHandler(clientChat::appendMessage);
    }

    private void setUserName(String nickname) {
        this.nickname = nickname;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect();
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendAuthMessage(login, pass);
    }

    public void sendMessage(String message) {
        try {
            networkService.sendMessage(message);
        } catch (IOException e) {
            showInfo("Ошибка!!!", "Failed to send message!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    private void showInfo(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
