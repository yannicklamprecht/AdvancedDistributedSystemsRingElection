package com.github.yannicklamprecht.ue2.messenger.local;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.messenger.Messenger;
import java.util.Optional;


public class LocalBufferMessenger implements Messenger<Void,Message> {

    private Message message;

    @Override
    public Message read() {
        if (message!=null){
            Message temp = new Message(message.getMessageType(), message.getId());

            message = null;
            return temp;
        }
        return null;
    }

    @Override
    public void write(Message message) {
        this.message = message;
    }

    @Override
    public Optional<Void> getConfigurator() {
        return Optional.empty();
    }
}
