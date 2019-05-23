package com.github.yannicklamprecht.ue2.message;


import java.io.Serializable;

public class Message implements Serializable {

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

    @Override
    public String toString() {
        return "Message{" +
        "messageType=" + messageType +
        ", id=" + id +
        '}';
    }
}
