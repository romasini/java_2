package ru.geekbrains.java2.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.authform.ClientAuthController;
import ru.geekbrains.java2.client.chatform.ClientChatController;

import javax.swing.*;
import java.io.IOException;

public class ClientController {

    private final NetworkService networkService;
    private final ClientAuthController authDialog;
    private final ClientChatController clientChat;
    private final Stage primaryStage;
    private final Parent rootAuth;
    private final Parent rootChat;
    private String nickname;

    public ClientController(String serverHost, int serverPort, Stage primaryStage) throws IOException {

        this.networkService = new NetworkService(serverHost, serverPort);
        this.primaryStage = primaryStage;

        FXMLLoader loaderAuth = new FXMLLoader();
        this.rootAuth = loaderAuth.load(getClass().getResourceAsStream("ClientAuthForm.fxml"));
        this.authDialog  = loaderAuth.getController();
        this.authDialog.setController(this);

        FXMLLoader loaderChat = new FXMLLoader();
        this.rootChat = loaderChat.load(getClass().getResourceAsStream("ClientChatForm.fxml"));
        this.clientChat  = loaderChat.getController();
        this.clientChat.setController(this);
    }

    public void runApplication() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(nickname -> {
            setUserName(nickname);
            openChat();
        });

        Scene scene = new Scene(rootAuth, 500, 230);
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest();

    }

    private void openChat() {
        //authDialog.dispose();
        //networkService.setMessageHandler(clientChat::appendMessage);
        //clientChat.setVisible(true);
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
