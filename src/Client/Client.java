package Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable
{
    private static Socket socket;
    private Controller controller;
    public String sendtext = "";
    public String ip;

    public Client(Controller controller) {
        this.controller = controller;
    }

    public void run()
    {
        try
        {
            String host = "10.10.100.110";
            int port = 8080;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);

            //Send the message to the server
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ip = socket.getLocalAddress().getHostAddress();
            String sendMessage = sendtext + ":" + ip;
            os.writeObject(sendMessage);
            os.flush();

            //Get the return message from the server
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            String message = (String)is.readObject();
            String ip = message.split(":")[1];

            if(!ip.equals(socket.getInetAddress().toString().split("/")[1])) {
                Platform.runLater(() -> controller.textarea.setText(message + "\n"));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}