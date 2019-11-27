package io;

import network.Message;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import client.ClientApplicationInterface;
import client.ClientMessageQueueInterface;

public class ClientMessageQueue extends TimerTask implements ClientMessageQueueInterface {
    private ClientApplicationInterface messageHandler;
    private Timer timer;
    private static int QUEUE_DELAY = 100;
    private Queue<Message> bufferedMessages = new LinkedList<>();

    public ClientMessageQueue(){
        this.timer = new Timer();
    }

    @Override
    public void run() {
        handleMessage();
    }

    @Override
    public void start() {
        this.timer.scheduleAtFixedRate(this, QUEUE_DELAY, QUEUE_DELAY*2);
    }

    @Override
    public void stop() {
        this.timer.cancel();
    }

    public void setMessageHandler(ClientApplicationInterface messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void addMessage(Message message){
        bufferedMessages.add(message);
    }

    public void handleMessage(){
        if(bufferedMessages.isEmpty() || messageHandler == null) return;

        Message message = bufferedMessages.remove();
        messageHandler.handleMessage(message);
    }

}
