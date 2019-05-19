package com.github.yannicklamprecht.ue2.messenger;

import com.github.yannicklamprecht.ue2.message.Message;


public class LocalBufferMessenger implements Messenger {

    private Message message;

    @Override
    public Message readMessage() {
        if (message!=null){
            Message temp = new Message(message.getMessageType(), message.getId());

            message = null;
            return temp;
        }
        return null;
    }

    @Override
    public void writeMessage(Message message) {
        this.message = message;
    }
}
