package com.github.yannicklamprecht.ue2.messenger.configuration;

import com.github.yannicklamprecht.ue2.messenger.Message;
import java.io.IOException;

/**
 * Created by ysl3000
 */
public class LocalConfiguration implements Configuration<LocalConfiguration, Message> {

    private volatile Message message;
    private LocalConfiguration next;

    public LocalConfiguration(int id) throws IOException {
    }

    @Override
    public void setNext(LocalConfiguration next) {
        this.next = next;
    }

    @Override
    public Message read() {
        if (message != null) {
            Message temp = message;
            this.message = null;
            return temp;
        }
        return null;
    }

    @Override
    public void write(Message message) {
        this.next.message = message;
    }
}
