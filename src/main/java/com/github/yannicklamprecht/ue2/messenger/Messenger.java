package com.github.yannicklamprecht.ue2.messenger;

import com.github.yannicklamprecht.ue2.message.Message;

public interface Messenger {

    Message readMessage();

    void writeMessage(Message message);

}
