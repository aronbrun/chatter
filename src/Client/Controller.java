package Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	public TextArea textarea;
	@FXML
	public TextField send_text;
	@FXML
	public Button btn_send;
	@FXML
	public AnchorPane pane;
	@FXML
	public ListView list;
	@FXML
	public ListView list1;
	public  ObservableList<String> items =FXCollections.observableArrayList ();
	public  ObservableList<String> items1 =FXCollections.observableArrayList ();
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    GUI.controller = this;

		btn_send.setOnAction(event -> {
			items1.add(send_text.getText());
			list1.setItems(items1);
			items.add("\n");
			Client.send(send_text.getText());
		});
	}
}