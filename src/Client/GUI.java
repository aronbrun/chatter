package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application {

    public static ControllerClient controllerClient;
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
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

