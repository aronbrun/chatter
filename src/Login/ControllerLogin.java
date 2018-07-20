package Login;

import Client.Client;
import Client.GUI;
import Server.ClientThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
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
	    LoginThread lt = new LoginThread();
	    new Thread(lt).start();
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
