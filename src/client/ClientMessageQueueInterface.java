package client;

import network.Message;

public interface ClientMessageQueueInterface {
    void setMessageHandler(ClientApplicationInterface messageHandler);

    void addMessage(Message message);

    void start();
    void stop();

}
