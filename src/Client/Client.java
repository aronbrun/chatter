package Client;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable
{
    private Socket socket;
    private ObjectInputStream din;
    private static ObjectOutputStream dout;

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            din = new ObjectInputStream(socket.getInputStream());
            dout = new ObjectOutputStream(socket.getOutputStream());
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Server ist aus oder keine Internet Verbindung");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while (true) {
                String message = null;
                try {
                    Object obj = din.readObject();
                    if (obj instanceof ArrayList) {
                        ArrayList<String> rawList = (ArrayList<String>) obj;
                        ObservableList<String> list = FXCollections.observableArrayList(rawList);
                        Platform.runLater(() -> {
                                    GUI.controller.list_names.setItems(list);
                                    GUI.controller.list_names.refresh();
                        });
                        // GUI.controller = null;
                        continue;
                    } else if (obj instanceof String) {
                        message = (String) obj;
                    } else {
                        continue;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(GUI.controller != null)
                {
                    final String finalMessage = message;
                    Platform.runLater(() -> {
                        GUI.controller.tabs.get(GUI.controller.chat).getitems.add(finalMessage);
                        GUI.controller.tabs.get(GUI.controller.chat).getfield.setItems(GUI.controller.tabs.get(GUI.controller.chat).getitems);
                        GUI.controller.tabs.get(GUI.controller.chat).senditems.add("\n");
                    });
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally
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

    public static void send(String text) {
        try {
            dout.writeObject((String) text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}