package Server;
import Client.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Server
{

	private static final HashMap<ClientThread, String> clientz = new HashMap<ClientThread, String>();
	public static void main(String[] args) {
		try {
			//trying to start server
			ServerSocket s = new ServerSocket(8080);
			System.out.println("----Server gestartet----");

			while (true) {
				//always accept clients
				Socket client = s.accept();
				if (client != null) {
					//starting new clienThread if client is not null
					ClientThread ct = new ClientThread(client);
					clientz.put(ct, client.getInetAddress().toString());
					new Thread(ct).start();
					//sending clientList to Client
					sendClientList();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendClientList() {
		// send new list to clients
		ArrayList<String> ipList = getIPList();
		for (ClientThread clientThread : getClientList()) {
			clientThread.send(ipList);
		}
	}

	public static ArrayList<ClientThread> getClientList() {
		return new ArrayList<>(clientz.keySet());
	}

	public static ArrayList<String> getIPList() {
		return clientz.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void removeClient(ClientThread clientThread) {
		clientz.remove(clientThread);
		sendClientList();
	}
	public static String getsendip(ClientThread clientThread) {
		return clientThread.getSocket().getInetAddress().toString();
	}
}