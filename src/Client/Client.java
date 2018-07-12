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
            String host = "localhost";
            int port = 8080;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);hallo

            //Send the message to the server
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ip = socket.getLocalAddress().getHostAddress();
            String sendMessage = sendtext + ":" + ip;
            os.writeObject(sendMessage);
            os.flush();

            //Get the return message from the server
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            String message = (String)is.readObject();
            Platform.runLater(() -> controller.label_1.setText(message));
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