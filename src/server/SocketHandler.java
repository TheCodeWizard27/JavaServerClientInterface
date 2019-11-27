package server;

import network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler {
    private Socket clientSocket;
    private ObjectInputStream objInputStream;
    private ObjectOutputStream objOutputStream;

    protected SocketHandler(Socket socket) throws IOException {
        clientSocket = socket;

        this.objOutputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.objOutputStream.flush();
        this.objInputStream = new ObjectInputStream(this.clientSocket.getInputStream());

    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) this.objInputStream.readObject();
    }

    public void send(Message message) throws IOException {
        this.objOutputStream.writeObject(message);
    }

    public boolean isConnected(){
        return clientSocket.isConnected();
    }

}
