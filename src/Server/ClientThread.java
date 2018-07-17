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

	public Socket getSocket() {
		return socket;
	}

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

	public void send(Object text) {
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
					for (ClientThread clientThread : Server.getClientList()) {
						if (!clientThread.equals(this)) {
							clientThread.send(input);
						}
					}

			}
		} catch (SocketException e) {
			Server.removeClient(this);
			Thread.currentThread().interrupt();
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
}