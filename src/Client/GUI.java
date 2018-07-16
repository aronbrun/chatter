package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public static Controller controller;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Chatter");
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

