package server;

import network.Message;

public interface ServerMessageQueueInterface {

    void setMessageHandler(ServerApplicationInterface messageHandler);

    void addMessage(String connectionId, Message message);

    void start();
    void stop();

}
