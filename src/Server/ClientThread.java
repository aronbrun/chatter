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
	private String iptosend;
	private String chattotake;
	private String[] inputparts;
	private String clientname;
	private String clientip;

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
					//get input
					input = (String) din.readObject();
					//split input into parts

					inputparts = input.split(":");
					input = inputparts[0];
					chattotake = inputparts[2];
					clientname = inputparts[3];
					clientip = inputparts[4];
					System.out.println(clientname + "ip: " + clientip);
					if(!inputparts[1].equals("")){
						iptosend = inputparts[1];
					}else{
						iptosend = "";
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				//send to one client
				if(iptosend != ""){
				for(int i = 0; i < Server.getClientList().size(); i++){
					if(Server.getClientList().get(i).getSocket().getInetAddress().toString().equals(iptosend)){
						Server.getClientList().get(i).send(input);
					}
				}} //Send to other clients
					else {
						for (ClientThread clientThread : Server.getClientList()) {
							if (!clientThread.equals(this)) {
								clientThread.send(input);
							}
						}
					}
			}
		} catch (SocketException e) {
			Server.removeClient(this);
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}