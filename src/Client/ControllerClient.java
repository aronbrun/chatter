package Client;

import Login.ControllerLogin;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerClient implements Initializable {
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
	@FXML
	public String ipsend = "";
	public static InetAddress inetAddress;
	public  ObservableList<String> items_get_4 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_4 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_1 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_1=FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_2 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_2 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_3 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_3 =FXCollections.observableArrayList ();
	public int chat =0;
	public ArrayList<Tab> tabs = new ArrayList<Tab>();
	private HashMap<Integer, String> ipchat = new HashMap<>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chats.getSelectionModel().selectFirst();
		GUI.controllerClient = this;
		tabs.add(new Tab("Chat1", list_get_1, list_send_1, items_get_1, items_send_1));
		tabs.add(new Tab("Chat2", list_get_2, list_send_2, items_get_2, items_send_2));
		tabs.add(new Tab("Chat3", list_get_3, list_send_3, items_get_3, items_send_3));
		tabs.add(new Tab("Chat4", list_get_4, list_send_4, items_get_4, items_send_4));


		list_names.setOnMouseClicked(event ->{
			try {
				ipsend = list_names.getSelectionModel().getSelectedItem().toString();
				if(ipsend.equals("groupchat")){
					chats.getSelectionModel();
				}
				if(!ipchat.containsValue(ipsend)){
					chats.getSelectionModel().select(chat);
					chat++;
					ipchat.put(chat - 1, ipsend);
				}else{
					for(int i =0; i < ipchat.size(); i++){
						if(ipchat.get(i).equals(ipsend)){
							chat = i + 1;
							chats.getSelectionModel().select(chat);
						}
					}
				}

			}catch (NullPointerException e){
			}
				});
		btn_send.setOnAction(event -> {
			try
			{
				inetAddress = InetAddress.getLocalHost();
			} catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			tabs.get(chat).senditems.add(send_text.getText());
			tabs.get(chat).sendfield.setItems(tabs.get(chat).senditems);
			tabs.get(chat).getitems.add("\n");
			Client.send(send_text.getText() + ":" + ipsend + ":" + (chat - 1) + ":" + ControllerLogin.username + ":" + inetAddress.getHostAddress().toString());
			send_text.clear();
		});
	}
}