package io;

import network.Message;

public class QueuedMessage {

    private String socketId;
    private Message message;

    public QueuedMessage(String socketId, Message message){
        this.socketId = socketId;
        this.message = message;
    }

    public String getSocketId(){
        return this.socketId;
    }
    public Message getMessage(){
        return this.message;
    }

}
