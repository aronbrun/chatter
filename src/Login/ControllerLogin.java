package Login;

import Client.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable
{
	Stage s = new Stage();
    @FXML
    Button btnContinue;
    @FXML
    TextField tfName;
	public static String username;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnContinue.setOnAction(event -> {
            try {
            	//getting username from TextField
	            username = tfName.getText();
	            //starting new GUI
	            GUI g = new GUI();
	            g.start(s);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        }
}
