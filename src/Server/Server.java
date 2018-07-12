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
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;


public class Server
{

	private static Socket socket;
	public static void main(String[] args)
	{
		try
		{
			int port = 8080;
			ServerSocket serverSocket = new ServerSocket(port);

			while(true)
			{
				System.out.println("aron");
				//Reading the message from the client
				socket = serverSocket.accept();
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
				String text = (String)is.readObject();

				System.out.println(text);
				String returnMessage;
				returnMessage = text;

				//Sending the response back to the client.
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				os.writeObject(returnMessage);
				os.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch(Exception e){}
		}
	}
}