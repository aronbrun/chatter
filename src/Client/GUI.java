package Client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("../Client/sample.fxml"));
		primaryStage.setTitle("Hello World");
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Connect to the Server
		Client client = new Client("10.10.100.110", 8080);

		// Runs a new Client
		Thread clientThread = new Thread(client);
		clientThread.start();
	}
	public static void main(String args[])
	{
		launch(args);

	}
}

