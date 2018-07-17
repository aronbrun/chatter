package Client;

import Server.Server;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.awt.event.MouseEvent;
import java.net.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	public JFXListView list_names;
	@FXML
	public TextField send_text;
	@FXML
	public Button btn_send;
	@FXML
	public AnchorPane pane;
	@FXML
	public ListView list_get;
	@FXML
	public ListView list_send;
	public  ObservableList<String> items_get =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send =FXCollections.observableArrayList ();
	public  ObservableList<String> chat_names =FXCollections.observableArrayList ();
	public String ipsend = "";
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    GUI.controller = this;

		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ipsend = socket.getLocalAddress().getHostAddress();
			chat_names.add(ipsend);
			list_names.setItems(chat_names);
		} catch (SocketException e)
		{
			e.printStackTrace();
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		list_names.setOnMouseClicked(event ->{
			ipsend = list_names.getSelectionModel().getSelectedItem().toString();
				});
		btn_send.setOnAction(event -> {
			items_send.add(send_text.getText());
			list_send.setItems(items_send);
			items_get.add("\n");
			Client.send(send_text.getText());
			send_text.clear();
		});
	}
}