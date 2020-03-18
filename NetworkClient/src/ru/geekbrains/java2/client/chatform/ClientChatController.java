package ru.geekbrains.java2.client.chatform;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.geekbrains.java2.client.ClientController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientChatController {

    @FXML
    private TextField enteredText;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea chatArea;

    @FXML
    private ListView<String> contactList;

    private Map<String, String> contactByMessage = new HashMap<>();
    private String currentContact;
    private ClientController controller;

    public void sendBtnAction(ActionEvent actionEvent) {
        String message  = enteredText.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        String ppp = chatArea.getText();
        //пишем в поле введенный нами текст и контакт сразу пишет ответ
        appendOwnMessage(message);
        controller.sendMessage(message);
        enteredText.clear();

    }

    private void appendOwnMessage(String message) {
        appendMessage("Я: " + message);
    }

    public void initialize(){
    }

    public void contactListClicked(MouseEvent mouseEvent) {

    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void appendMessage(String message) {
        Platform.runLater(()-> {
            chatArea.appendText(message);
            chatArea.appendText(System.lineSeparator());
        });
    }
}
