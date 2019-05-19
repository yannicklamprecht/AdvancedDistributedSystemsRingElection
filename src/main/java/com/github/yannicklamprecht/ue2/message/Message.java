package com.github.yannicklamprecht.ue2.message;


public class Message {

    private final MessageType messageType;
    private int id;

    public Message(MessageType messageType, int id) {
        this.messageType = messageType;
        this.id = id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
