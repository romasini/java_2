package ru.geekbrains.java2.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientAuth extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("ClientAuthForm.fxml"));
        controller = loader.getController();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Echo-Client");
        primaryStage.setScene(scene);
        primaryStage.setIconified(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            controller.exitApplication();//закроем соединение
            Platform.exit();//закроем поток Формы
            System.exit(0);//если закроется нормально, то вернет 0 в консоль
        });
    }
}
