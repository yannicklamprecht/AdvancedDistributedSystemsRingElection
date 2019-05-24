package com.github.yannicklamprecht.ue2.messenger.configuration;

import com.github.yannicklamprecht.ue2.messenger.Message;
import java.io.IOException;

/**
 * Created by ysl3000
 */
public class LocalConfiguration implements Configuration<LocalConfiguration, Message> {

    private Message current;
    private LocalConfiguration next;

    public LocalConfiguration(int id) throws IOException {
    }

    @Override
    public void setNext(LocalConfiguration next) {
        this.next = next;
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
