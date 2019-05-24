package com.github.yannicklamprecht.ue2.messenger.configuration.local;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.messenger.configuration.Configuration;

/**
 * Created by ysl3000
 */
public class LocalConfiguration implements Configuration<LocalConfiguration, Message> {

    private Message current;
    private LocalConfiguration next;

    @Override
    public void setNext(LocalConfiguration next) {
        this.next = next;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public Message read() {
        if (current != null) {
            Message temp = new Message(current.getMessageType(), current.getId());
            this.current = null;
            return temp;
        }
        return null;
    }

    @Override
    public void write(Message message) {
        this.next.current=message;
    }
}
