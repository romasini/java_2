package lesson4.homeWork4;

import com.sun.javaws.IconUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.*;

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

    public void sendBtnAction(ActionEvent actionEvent) {
        String entString = enteredText.getText();
        if (!entString.isEmpty()) {
            String ppp = chatArea.getText();
            //пишем в поле введенный нами текст и контакт сразу пишет ответ
            chatArea.setText(String.format("%s %n %s: %s %n %s: %s",ppp,"Me",entString, currentContact, "что-то пишет..." ));
            contactByMessage.put(currentContact, chatArea.getText());
            enteredText.clear();
        }

    }

    public void initialize(){

        contactByMessage.put("Bobby","");
        contactByMessage.put("Dobby","");
        contactByMessage.put("Robby","");
        contactByMessage.put("Mobby","");

        List<String> contactArray = new ArrayList<>();
        contactByMessage.forEach((contact,contactMessage)->{
            contactArray.add(contact);
        });
        ObservableList<String> contacts = FXCollections.observableArrayList(contactArray);
        contactList.setItems(contacts);
        contactList.getSelectionModel().select(0);
        currentContact = contactList.getSelectionModel().getSelectedItem();

    }

    public void contactListClicked(MouseEvent mouseEvent) {
        currentContact = contactList.getSelectionModel().getSelectedItem();
        if (currentContact!=null){
            chatArea.setText(contactByMessage.get(currentContact));
        }
    }
}
