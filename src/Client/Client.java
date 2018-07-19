package Client;

import Login.Login;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import Login.ControllerLogin;

public class Client implements Runnable
{
    private Socket socket;
    private ObjectInputStream din;
    private static ObjectOutputStream dout;

    public Client(String ip, int port) {
        try {
            //trying to create new Socket
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
                    //getting object from Server and deciding if it is String or ArrayList
                    Object obj = din.readObject();
                    if (obj instanceof ArrayList) {
                        ArrayList<String> rawList = (ArrayList<String>) obj;
                        ObservableList<String> list = FXCollections.observableArrayList(rawList);
                        //adding groupchat to list
                        list.add("groupchat");
                        list.remove("/" + GUI.controllerClient.clientip);
                        Platform.runLater(() -> {
                                    GUI.controllerClient.list_names.setItems(list);
                                    GUI.controllerClient.list_names.refresh();
                                    GUI.controllerClient.list_names.getSelectionModel().select(0);
                                    GUI.controllerClient.chats.getSelectionModel().getSelectedIndex();
                                    GUI.controllerClient.ipsend = GUI.controllerClient.list_names.getSelectionModel().getSelectedItem().toString();
                        });
                        // GUI.controllerClient = null;
                        continue;
                    } else if (obj instanceof String) {
                        message = (String) obj;
                    } else {
                        continue;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(GUI.controllerClient != null)
                {
                    final String finalMessage = message;
                    Platform.runLater(() -> {
                        GUI.controllerClient.tabs.get(GUI.controllerClient.chat).getitems.add(finalMessage);
                        GUI.controllerClient.tabs.get(GUI.controllerClient.chat).getfield.setItems(GUI.controllerClient.tabs.get(GUI.controllerClient.chat).getitems);
                        GUI.controllerClient.tabs.get(GUI.controllerClient.chat).senditems.add("\n");
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