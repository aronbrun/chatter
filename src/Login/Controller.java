package Login;

import Client.Client;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    Button btnContinue;
    @FXML
    TextField tfNumber;
    @FXML
    TextField tfName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnContinue.setOnAction(event -> {
            AuthMethod auth = new TokenAuthMethod("746702b4", "bg77UTqIJ8cdPmPq");
            NexmoClient client = new NexmoClient(auth);

            try
            {
                System.out.println("clicked: 0041" + tfNumber.getText());
                SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                        "Chatter",
                        "0041"+ tfNumber.getText(),
                        "Hallo " + tfName.getText() + "\nDein Verifizierungscode ist: " + (int) (Math.random()*9999) + 1 + "\n\n\n"));
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (NexmoClientException e)
            {
                e.printStackTrace();
            }

            Stage comfirm = new Stage();
            Parent root = null;
            try
            {
                root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                comfirm.setTitle("Bestätigung");
                comfirm.setResizable(false);
                comfirm.setScene(new Scene(root, 202, 199));
                comfirm.show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        }
}
