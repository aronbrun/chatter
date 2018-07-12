package Server;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;


public class Server
{

	public static ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(8080);
			System.out.println("----Server gestartet----");

			while (true) {
				Socket client = s.accept();
				if (client != null) {
					ClientThread ct = new ClientThread(client);
					clients.add(ct);
					new Thread(ct).start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}