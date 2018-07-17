package Client;

import Server.Server;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import java.awt.event.MouseEvent;
import java.net.*;
import java.util.ArrayList;
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
	public ListView list_get_1;
	@FXML
	public ListView list_send_1;
	@FXML
	public ListView list_get_2;
	@FXML
	public ListView list_send_2;
	@FXML
	public ListView list_get_3;
	@FXML
	public ListView list_send_3;
	@FXML
	public ListView list_get_4;
	@FXML
	public ListView list_send_4;
	@FXML
	public TabPane chats;
	public int i =0;

	public  ObservableList<String> items_get =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send =FXCollections.observableArrayList ();
	public String ipsend = "";
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Tab> tabs = new ArrayList<Tab>();
		tabs.add(new Tab("Chat1", "list_get_1", "list_send_1"));
		tabs.add(new Tab("Chat2", "list_get_2", "list_send_2"));
		tabs.add(new Tab("Chat3", "list_get_3", "list_send_3"));
		tabs.add(new Tab("Chat4", "list_get_4", "list_send_4"));
	    GUI.controller = this;

		list_names.setOnMouseClicked(event ->{
			try {
				if(!ipsend.equals(list_names.getSelectionModel().getSelectedItem().toString())){
					chats.getSelectionModel().select(i);
					i++;
				}
				ipsend = list_names.getSelectionModel().getSelectedItem().toString();
			}catch (NullPointerException e){
			}
				});
		btn_send.setOnAction(event -> {
			items_send.add(send_text.getText());
			list_send_1.setItems(items_send);
			items_get.add("\n");
			Client.send(send_text.getText() + ":" + ipsend + ":" + (i - 1));
			send_text.clear();
		});
	}
}