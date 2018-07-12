package Login;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AuthMethod auth = new TokenAuthMethod("746702b4", "bg77UTqIJ8cdPmPq");
        NexmoClient client = new NexmoClient(auth);

        try
        {
            SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                    "Chatter",
                    "0041794496199",
                    "Dein Verifizierungscode ist: " + (int) (Math.random()*9999) + 1));
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NexmoClientException e)
        {
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 211, 105));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
