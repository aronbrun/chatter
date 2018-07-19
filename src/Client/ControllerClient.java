package Client;

import Login.ControllerLogin;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerClient implements Initializable {
	//creating variables
	@FXML
	public JFXListView list_names;
	@FXML
	public TextArea send_text;
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
	public ListView list_get_5;
	@FXML
	public ListView list_send_5;
	@FXML
	public ListView list_get_6;
	@FXML
	public ListView list_send_6;
	@FXML
	public TabPane chats;
	@FXML
	public String ipsend = "";
	public String clientname;
	public String clientip;

	//making Observablelists for the chats
	public  ObservableList<String> items_get_4 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_4 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_1 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_1=FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_2 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_2 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_3 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_3 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_5 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_5 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_get_6 =FXCollections.observableArrayList ();
	public  ObservableList<String> items_send_6 =FXCollections.observableArrayList ();
	public int chat =0;
	public ArrayList<Tab> tabs = new ArrayList<Tab>();
	private HashMap<Integer, String> ipchat = new HashMap<>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//setting up chats to chat in
		GUI.controllerClient = this;
		tabs.add(new Tab("Chat1", list_get_1, list_send_1, items_get_1, items_send_1));
		tabs.add(new Tab("Chat2", list_get_2, list_send_2, items_get_2, items_send_2));
		tabs.add(new Tab("Chat3", list_get_3, list_send_3, items_get_3, items_send_3));
		tabs.add(new Tab("Chat4", list_get_4, list_send_4, items_get_4, items_send_4));
		tabs.add(new Tab("Chat5", list_get_5, list_send_5, items_get_5, items_send_5));
		tabs.add(new Tab("Chat6", list_get_6, list_send_6, items_get_6, items_send_6));
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {	
			e.printStackTrace();
		}
		//sending clinetname and ip to database
		clientname = ControllerLogin.username;
		clientip = inetAddress.getHostAddress();

		list_names.setFocusTraversable(false);

		list_get_1.setMouseTransparent( true );
		list_get_1.setFocusTraversable( false );
		list_send_1.setMouseTransparent( true );
		list_send_1.setFocusTraversable( false );

		list_get_2.setMouseTransparent( true );
		list_get_2.setFocusTraversable( false );
		list_send_2.setMouseTransparent( true );
		list_send_2.setFocusTraversable( false );

		list_get_6.setMouseTransparent( true );
		list_get_6.setFocusTraversable( false );
		list_send_6.setMouseTransparent( true );
		list_send_6.setFocusTraversable( false );

		list_get_3.setMouseTransparent( true );
		list_get_3.setFocusTraversable( false );
		list_send_3.setMouseTransparent( true );
		list_send_3.setFocusTraversable( false );

		list_get_4.setMouseTransparent( true );
		list_get_4.setFocusTraversable( false );
		list_send_4.setMouseTransparent( true );
		list_send_4.setFocusTraversable( false );

		list_get_5.setMouseTransparent( true );
		list_get_5.setFocusTraversable( false );
		list_send_5.setMouseTransparent( true );
		list_send_5.setFocusTraversable( false );

		//choosing chat to send text on
		list_names.setOnMouseClicked(event ->{
			try {
				ipsend = list_names.getSelectionModel().getSelectedItem().toString();
				if(ipsend.equals("groupchat")){
					chats.getSelectionModel().select(chat);
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

		// shift enter -> tab
		send_text.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) {
				System.out.println("shift");
				send_text.appendText("\n");
			}
			else if (event.getCode() == KeyCode.ENTER) {
				System.out.println("send");
				sendtext();
				send_text.setText("");
			}
		});

		//setting listeners to send to Server
		btn_send.setOnAction(event -> {
			sendtext();
		});
	}
	//method to send to Server
	public void sendtext(){
		if(send_text.getText().equals("") || validIP(send_text.getText()) == true){
			send_text.clear();
		}else {
			tabs.get(chat).senditems.add(send_text.getText());
			tabs.get(chat).sendfield.setItems(tabs.get(chat).senditems);
			tabs.get(chat).getitems.add("\n");
			Client.send(send_text.getText() + ":" + ipsend + ":" + (chat - 1));
			send_text.clear();
		}
	}
	// method to validate ip from send_text
	public static boolean validIP (String ipbefore) {

		String[] parts = ipbefore.split(":");
		if(parts.length > 1){
			ipbefore = parts[1];
			ipbefore = ipbefore.replace("/", "");
		}
		try {
			if ( ipbefore == null || ipbefore.isEmpty() ) {
				return false;
			}

			String[] partsa = ipbefore.split( "\\." );
			if ( partsa.length != 4 ) {
				return false;
			}

			for ( String s : partsa ) {
				int i = Integer.parseInt( s );
				if ( (i < 0) || (i > 255) ) {
					return false;
				}
			}
			if ( ipbefore.endsWith(".") ) {
				return false;
			}

			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}