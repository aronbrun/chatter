package Login;

import Client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    Button btnContinue = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnContinue.setOnAction(event -> {
            System.out.println("aron");
        });
        }
}
