package Login;

import javafx.application.Platform;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginThread implements Runnable{
	private Socket socket;
	private ObjectInputStream din;
	private static ObjectOutputStream dout;
	public static void send(String text) {
		try {
			dout.writeObject((String) text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			//trying to create new Socket
			socket = new Socket("10.10.100.110", 8080);
			din = new ObjectInputStream(socket.getInputStream());
			dout = new ObjectOutputStream(socket.getOutputStream());
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Server ist aus oder keine Internet Verbindung");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		final String inet = inetAddress.getHostAddress();
		Platform.runLater(() ->{
			send(inet);
		});
		while (true) {
			try {
				//reading message from Server
				Object obj = din.readObject();
				String getmessage = (String) obj;
				if(getmessage.equals("true")){
					Login.handle();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	}
	}
}
