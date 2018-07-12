package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	public TextArea textarea;
	@FXML
	public TextField send_text;
	@FXML
	public Button btn_send;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_send.setOnAction(event -> {
			Client client = new Client(this);
			client.sendtext = send_text.getText();
			new Thread(client).start();
		});
	}

}
