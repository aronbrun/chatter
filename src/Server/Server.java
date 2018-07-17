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
			ServerSocket s = new ServerSocket(8080);
			System.out.println("----Server gestartet----");

			while (true) {
				Socket client = s.accept();
				if (client != null) {
					//chat_names.add(client.getInetAddress().toString());
					//System.out.println(chat_names.size());
					// GUI.controller.list_names.setItems(chat_names);

					ClientThread ct = new ClientThread(client);
					clientz.put(ct, client.getInetAddress().toString());
					new Thread(ct).start();

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
}