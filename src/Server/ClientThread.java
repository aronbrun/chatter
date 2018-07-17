package Server;

import Client.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable {
	private Socket socket;
	private ObjectOutputStream dout;
	private ObjectInputStream din;
	private String input;

	ClientThread(Socket socket) {
		this.socket = socket;
		try {
			this.dout = new ObjectOutputStream(socket.getOutputStream());
			this.din = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	private void send(String text) {
		try {
			dout.writeObject(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					input = (String) din.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				// Send to other clients
					for (ClientThread clientThread : Server.clients) {
						if (!clientThread.equals(this)) {
							GUI.controller.chat_names.add(clientThread.socket.getInetAddress().toString());
							clientThread.send(input);
							System.out.println(input + " sent to " + clientThread.socket.getInetAddress());
						}
					}

			}
		} catch (SocketException e) {
			Server.clients.remove(this);
			Thread.currentThread().interrupt();
		} catch (IOException e) {
		}
	}
}