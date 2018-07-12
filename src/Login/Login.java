package Login;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

import java.io.IOException;

public class Login
{
    public static void main(String[] args){
        AuthMethod auth = new TokenAuthMethod("746702b4", "bg77UTqIJ8cdPmPq");
        NexmoClient client = new NexmoClient(auth);

        try
        {
            SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                    "Chatter",
                    "0041791994779",
                    "Dein Verifizierungscode ist: " + (int) (Math.random()*9999) + 1));
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NexmoClientException e)
        {
            e.printStackTrace();
        }
    }
}
