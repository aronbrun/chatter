package Client;

import Login.ControllerLogin;
import Login.Login;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GUI extends Application {
	public static ControllerClient controllerClient = new ControllerClient();
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
		Scene scene = new Scene(root, 1100, 700);
		primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
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

