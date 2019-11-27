package io;

import network.Message;
import server.ServerApplicationInterface;
import server.ServerMessageQueueInterface;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class ServerMessageQueue extends Timer implements ServerMessageQueueInterface {
    private ServerApplicationInterface messageHandler;
    private static int QUEUE_DELAY = 100;
    private Queue<QueuedMessage> bufferedMessages = new LinkedList<>();

    public ServerMessageQueue(){
        super(QUEUE_DELAY, null);
        this.addActionListener(e -> handleMessage());
    }

    public void setMessageHandler(ServerApplicationInterface messageHandler){
        this.messageHandler = messageHandler;
    }

    public void addMessage(String connectionId, Message message){
        bufferedMessages.add(new QueuedMessage(connectionId, message));
    }

    private void handleMessage() {
        if(bufferedMessages.isEmpty() || messageHandler == null) return;

        QueuedMessage tempMessage = bufferedMessages.remove();
        messageHandler.handleMessage(tempMessage.getMessage(), tempMessage.getSocketId());
    }
}
