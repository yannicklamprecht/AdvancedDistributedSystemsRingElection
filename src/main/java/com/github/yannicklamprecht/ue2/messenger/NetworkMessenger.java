package com.github.yannicklamprecht.ue2.messenger;

import com.github.yannicklamprecht.ue2.message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ysl3000
 */
public class NetworkMessenger implements Messenger {

    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public NetworkMessenger(ObjectOutputStream writer, ObjectInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public Message readMessage() {
        try {
            return (Message) reader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeMessage(Message message) {
        try {
            writer.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
