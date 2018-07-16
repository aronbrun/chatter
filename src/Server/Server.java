package Server;
import Client.GUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


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