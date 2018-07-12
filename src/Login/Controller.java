package Login;

import Client.Client;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    Button btnContinue;
    Label lblNumber;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnContinue.setOnAction(event -> {
            AuthMethod auth = new TokenAuthMethod("746702b4", "bg77UTqIJ8cdPmPq");
            NexmoClient client = new NexmoClient(auth);

            try
            {
                SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                        "Chatter",
                        lblNumber.getText(),
                        "Dein Verifizierungscode ist: " + (int) (Math.random()*9999) + 1));
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (NexmoClientException e)
            {
                e.printStackTrace();
            }
        });
        }
}
