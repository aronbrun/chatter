package Client;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

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
                    message = (String) din.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(GUI.controller != null)
                {
                    GUI.controller.textarea.setText(GUI.controller.textarea.getText() + "\n" + message);
                    GUI.controller.send_text.clear();
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