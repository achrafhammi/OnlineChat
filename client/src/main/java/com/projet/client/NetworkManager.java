package com.projet.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class NetworkManager {
    private static NetworkManager instance;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private NetworkManager() {
        // Private constructor to prevent instantiation
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void connectToServer(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendObject(Object obj) throws IOException {
        System.out.println("object to  send : "+ obj);
        oos.writeObject(obj);
        oos.flush();
    }

    public Object receiveObject() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public void closeConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
/*
    //listen for new messages from the server to refresh messages ui
    public void listenForMessages() {
        new Thread(() -> {
            try {
                while (true) {
                    Object receivedObject = receiveObject();
                    System.out.println("receivedObject: " + receivedObject);
                    if (receivedObject instanceof HashMap) {
                        HashMap<String, String> receivedData = (HashMap<String, String>) receivedObject;
                        if (receivedData.get("type").equals("newMessage")) {
                            System.out.println("new message received");
                            int id  = Integer.parseInt(receivedData.get("idSender"));
                            // refresh messages ui
                            mainController mainController = new mainController();

                            mainController.refreshMessages(id);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    */

}
