package client;

import network.Message;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class IOClient extends ServerProxy implements Runnable {
    private Socket clientSocket;
    private ClientMessageQueueInterface queue;
    private Thread messageReceiveThread;
    private InetSocketAddress address;

    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public IOClient(ClientApplicationInterface clientApplicationInterface) {
        super(clientApplicationInterface);

        /*
        Instanziert und startet den Thread zum Erhalten
        von Messages und weist ihm die run Methode dieser Klasse zu.
         */
        this.messageReceiveThread = new Thread(this);
    }

    public void setSocket(Socket socket){
        this.clientSocket = socket;
    }

    public void setQueue(ClientMessageQueueInterface clientMessageQueue){
        this.queue = clientMessageQueue;
        this.queue.setMessageHandler(this.clientApplication);
    }

    public void setAddress(InetSocketAddress address){
        this.address = address;
    }

    public void connect() throws IOException {
        this.clientSocket.connect(this.address);

        this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());
        this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());

        this.queue.start();
        this.messageReceiveThread.start();
    }

    public void disconnect() throws IOException {
        this.clientSocket.close();
        this.queue.stop();
    }

    @Override
    public void run() {
        try {
            while (this.clientSocket.isConnected()) {
                try {
                    Message message = (Message) this.inStream.readObject();
                    if (message == null) return;

                    this.queue.addMessage(message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection lost");
        }
    }


    @Override
    public void send(Message message) {
        try{
            this.outStream.writeObject(message);
        }catch (Exception e){
            System.out.println("Unable to send message");
        }
    }
}
