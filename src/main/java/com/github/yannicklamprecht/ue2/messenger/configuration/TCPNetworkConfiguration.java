package com.github.yannicklamprecht.ue2.messenger.configuration;

import com.github.yannicklamprecht.ue2.messenger.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ysl3000
 */
public class TCPNetworkConfiguration implements Configuration<TCPNetworkConfiguration, Message> {

    private Socket out;
    private ServerSocket in;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    public TCPNetworkConfiguration(int id) throws IOException {
        this.in = new ServerSocket(2000 + id);
    }

    public Message read() {
        try {
            return (Message) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(Message message) {
        try {
            if (objectOutputStream == null && out != null) {
                this.objectOutputStream = new ObjectOutputStream(out.getOutputStream());
            }

            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNext(TCPNetworkConfiguration next) {
        try {
            this.out = new Socket(next.in.getInetAddress(), next.in.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "NetworkConfiguration{" +
        "out=" + out +
        ", in=" + in +
        ", objectInputStream=" + objectInputStream +
        ", objectOutputStream=" + objectOutputStream +
        '}';
    }

    public void init() {
        if (objectInputStream == null) {

            try {
                Socket socket = in.accept();
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
