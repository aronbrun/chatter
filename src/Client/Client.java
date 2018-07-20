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
import java.util.Arrays;
import java.util.HashMap;

import Login.ControllerLogin;

public class Client implements Runnable
{
    private Socket socket;
    private ObjectInputStream din;
    private static ObjectOutputStream dout;
    public  static HashMap<String, String> hash = new HashMap<>();

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
                        System.out.println(Arrays.toString(rawList.toArray()));
                        ArrayList<String> listnames = new ArrayList<>();
                        ArrayList<String> listips = new ArrayList<>();
                        String[] partsnameip;
                        System.out.println(rawList.size());
                        for(int i= 0; i < rawList.size(); i++){
                            partsnameip = rawList.get(i).split(":");
                            System.out.println(partsnameip[0] + "-" + GUI.controllerClient.clientname);
                            if(!partsnameip[0].equals(GUI.controllerClient.clientname)){
                                System.out.println(partsnameip[0] + " - " + GUI.controllerClient.clientname);
                                listnames.add(partsnameip[0]);
                            }
                            hash.put(partsnameip[0], partsnameip[1]);
                            listips.add(partsnameip[1]);
                        }
                        ObservableList<String> listall = FXCollections.observableArrayList(rawList);
                        ObservableList<String> listip = FXCollections.observableArrayList(listips);
                        ObservableList<String> listname = FXCollections.observableArrayList(listnames);
                        //adding groupchat to list
                        listname.add("groupchat");
                        System.out.println("sick");

                        Platform.runLater(() -> {
                                    GUI.controllerClient.list_names.setItems(listname);
                                    GUI.controllerClient.list_names.refresh();
                                    GUI.controllerClient.list_names.getSelectionModel().select(0);
                                    GUI.controllerClient.chats.getSelectionModel().getSelectedIndex();
                                    GUI.controllerClient.ipsend = hash.get(GUI.controllerClient.list_names.getSelectionModel().getSelectedItem().toString());
                        });
                        // GUI.controllerClient = null;
                        continue;
                    } else if (obj instanceof String) {
                        message = (String) obj;
                    }else {
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