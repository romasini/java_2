package ru.geekbrains.java2.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientChat extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClientChatForm.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Super-Duper-Chat");
        primaryStage.setScene(scene);
        primaryStage.setIconified(false);
        primaryStage.show();


    }
}
