package ru.geekbrains.java2.client.controller;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.model.NetworkService;
import ru.geekbrains.java2.client.view.authform.ClientAuthController;
import ru.geekbrains.java2.client.view.chatform.ClientChatController;

import java.io.IOException;
import java.util.List;

public class ClientController {

    private final NetworkService networkService;
    private Stage primaryStage;
    private Parent rootChat;
    private ClientAuthController authController;
    private ClientChatController chatController;
    private Scene authScene;
    private Scene chatScene;
    private String nickname;
    private String login;

    public ClientController(String serverHost, int serverPort, Stage primaryStage) throws IOException {

        this.networkService = new NetworkService(serverHost, serverPort);
        this.primaryStage = primaryStage;

        FXMLLoader loaderAuth = new FXMLLoader();
        rootChat = loaderAuth.load(ClientAuthController.class.getResourceAsStream("ClientAuthForm.fxml"));
        authController = loaderAuth.getController();
        authController.setController(this);
        authScene = new Scene(rootChat, 500, 230);

        FXMLLoader loaderChat = new FXMLLoader();
        rootChat = loaderChat.load(ClientChatController.class.getResourceAsStream("ClientChatForm.fxml"));
        chatController = loaderChat.getController();
        chatController.setController(this);
        chatScene = new Scene(rootChat, 600, 400);
        }

    public void runApplication() throws IOException {
        openAuth();
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() throws IOException {
        networkService.setSuccessfulAuthEvent((login,nickname) -> {
            setUserName(nickname);
            setLogin(login);
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

        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(authScene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.exit(0);
        });
    }

    private void openChat() throws IOException {

        primaryStage.setTitle(getTitle());
        primaryStage.setScene(chatScene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            shutdown();
            System.exit(0);
        });

        networkService.setMessageHandler(chatController::appendMessage);
    }

    private void setUserName(String nickname) {
        this.nickname = nickname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect(this);
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendCommand(Command.authCommand(login, pass));
    }

    public void sendMessageToAllUsers(String message) {
        try {
            networkService.sendCommand(Command.broadcastMessageCommand(message));
        } catch (IOException e) {
            chatController.showInfo("Ошибка!!!", "Failed to send message!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }

    public void sendPrivateMessage(String username, String message){
        try {
            networkService.sendCommand(Command.privateMessageCommand(username, message));
        } catch (IOException e) {
            chatController.showInfo("Ошибка!!!",e.getMessage());
        }
    }

    public void showErrorMessage(String errorMessage){
        Platform.runLater(()->{
            if(chatController != null){
                chatController.showInfo("Ошибка!!!", errorMessage);
            }else if(authController != null){
                authController.showInfo("Ошибка!!!", errorMessage);
            }
            System.err.println(errorMessage);
        });
    }

    public void updateUsersList(List<String> users) {
        users.remove(nickname);
        users.add(0, "All");
        if(chatController != null)  chatController.updateUsers(users);
    }

    public void sendChangeNicknameCommand(String newNick) {
        try{
            networkService.sendCommand(Command.changeNicknameCommand(getLogin(), newNick));
        } catch (IOException e) {
            chatController.showInfo("Ошибка!!!",e.getMessage());
        }

    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        Platform.runLater(()->{
            primaryStage.setTitle(getTitle());
            chatController.showInfo("Успех", "Ник успешно изменен");
        });

    }

    private String getTitle(){
        return "Супер чат :" + nickname;
    }
}
