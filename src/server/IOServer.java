package server;

import network.Message;
import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import logger.Logger;

public class IOServer extends Server implements Runnable{
    private Map<String, SocketHandler> sockets = new HashMap();
    private ServerSocket serverSocket;
    private Thread connectionThread;
    private ServerMessageQueueInterface serverMsgQueue;

    /**
     * Konstruktor. Initialisiert das neue Server-Objekt mit der Referenz auf das Empf?øΩngerobjekt.
     *
     * @param msgHandler Das Empf?øΩngerobjekt des Bomberman-Servers f?øΩr Nachrichten.
     */
    public IOServer(ServerApplicationInterface msgHandler) {
        super(msgHandler);
        this.connectionThread = new Thread(this);
    }

    /**
     * Starts Listening loop.
     * @throws SocketException if socket is closed or stopListening has been called.
     */
    public void startListening() throws SocketException {
        this.serverMsgQueue.start();                                        // Start thread that handles queued messages.

        if(this.serverSocket.isClosed())
            throw new SocketException();

        this.connectionThread.start();                                      // Start thread that handles incoming connections and messages.
    }

    /**
     * Closes Socket.
     * @throws IOException on closing serverSocket.
     */
    public void stopListening() throws IOException {
        this.serverSocket.close();
        this.serverMsgQueue.stop();
    }

    public void setServerSocket(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void setMessageQueue(ServerMessageQueueInterface serverMsgQueue){
        this.serverMsgQueue = serverMsgQueue;
        this.serverMsgQueue.setMessageHandler(this.serverApplication);
    }

    /**
     * Handles the Server Loop managing connections.
     */
    @Override
    public void run() {

        while(!serverSocket.isClosed()){
            try{
                SocketHandler socket = new SocketHandler(this.serverSocket.accept());   // Blocks Thread until connection is made.
                String tempUUID = UUID.randomUUID().toString();                         // Generate UUID to identify Socket.

                // Add socket to map with generated id.
                this.sockets.put(tempUUID, socket);

                System.out.println(tempUUID + " : Connected");

                // Create and start Handle Thread that listens to messages sent by the Client.
                createSocketListeningThread(tempUUID, socket).start();

            }catch(Exception ex){
                Logger.getInstance().writeMessage("*Catches fatal exception* OWO | In Listening Thread. Details : " + ex.getMessage());
            }
        }

    }

    private Thread createSocketListeningThread(String uuid, SocketHandler socket){
        return new Thread(() -> {

            try{
                while(socket.isConnected()){
                    Message receivedMessage = socket.readMessage();

                    if(this.serverMsgQueue != null)
                        serverMsgQueue.addMessage(uuid, receivedMessage);
                }
            }catch(Exception ex){
                Logger.getInstance().writeMessage("*Hwuh* √?w√? | Connection to Client <" + uuid + "> abruptly lost.");
            }

            // Remove disconnected Socket
            this.sockets.remove(uuid);

        });
    }

    /**
     *
     * @param message Das Nachrichtenobjekt, welches versendet werden soll.
     * @param connectionId Die ID der Netzwerkverbindung zum Client.
     */
    @Override
    public void send(Message message, String connectionId) {

        if(this.sockets.containsKey(connectionId)) {
            System.out.println("*Hewwoo..* Owo | No client with Id <" + connectionId + "> found.");
            return;
        }

        SocketHandler socket = this.sockets.get(connectionId);

        try{
            socket.send(message);
        }catch(Exception ex){
            System.out.println("*Hewwoo* owO | Couldn't reach Client with Id <" + connectionId + ">.");
        }
    }

    /**
     * Sends message to all connected clients.
     * @param message Das Nachrichtenobjekt, welches versendet werden soll.
     */
    @Override
    public void broadcast(Message message) {
        for(Map.Entry<String, SocketHandler> entry : sockets.entrySet()){

            try{
                SocketHandler socket = entry.getValue();
                socket.send(message);
            }catch (Exception e){
                System.out.println("*Hewwoo* owO | Couldn't reach Client with Id <" + entry.getKey() + ">.");
            }

        }
    }
}
