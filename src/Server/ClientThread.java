package Server;

import Client.GUI;

import java.io.*;
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
	private String ipfrom;
	private String ipto;
	private String[] ipfromparts;
	private String[] iptoparts;
	private int iptoidentifier;
	private int ipfromidentifier;
	private String ipone;
	private String iptwo;
	private String finalname;
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
					//getting input
					input = (String) din.readObject();
					//splitting input into parts
					inputparts = input.split(":");
					input = inputparts[0];
					chattotake = inputparts[2];
					if(!inputparts[1].equals("")){
						iptosend = inputparts[1];
					}else{
						iptosend = "";
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				//creating txt files on Server for chat course
				ipfrom = Server.getsendip(this);
				ipto = iptosend.replace("/", "");
				ipfrom = ipfrom.replace("/", "");
				ipfromparts = ipfrom.split("\\.");
				iptoparts = ipto.split("\\.");
				if(!ipto.equals("groupchat")) {
					ipfromidentifier = Integer.parseInt(ipfromparts[3]);
					iptoidentifier = Integer.parseInt(iptoparts[3]);
					if (ipfromidentifier > iptoidentifier) {
						ipone = ipto;
						iptwo = ipfrom;
						finalname = ipone + "--" + iptwo;
					} else {
						ipone = ipfrom;
						iptwo = ipto;
						finalname = ipone + "--" + iptwo;
					}
				}else {
						finalname = "groupchat";
				}
				String path = "D://Work//Source//github//src//chatlogs//" + finalname + ".txt";
				File f = new File(path);
				if(!f.exists()){
					f.createNewFile();
				}
				FileWriter fw = new FileWriter(f,true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.println("[" + input + "]:::::::::::"  + ipfrom + "---->" + ipto);
				System.out.println("[" + input + "]:::::::::::"  + ipfrom + "---->" + ipto);
				pw.close();
				//sending to one client
				if(!iptosend.equals("") && !iptosend.equals("groupchat")){
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