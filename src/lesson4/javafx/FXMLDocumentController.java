package lesson4.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController {

    @FXML
    private Label label123;

    @FXML
    private Button button;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label123.setText("Hello World!");
    }

    @FXML
    public void mouseEnterAction(MouseEvent mouseEvent) {
        System.out.println("Mouse was entered");
    }
}
