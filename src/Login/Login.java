package Login;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Login/login.fxml"));
        primaryStage.setTitle("Regsitrierung");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 202, 180));
        primaryStage.hide();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
