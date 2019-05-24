package com.github.yannicklamprecht.ue2.messenger.configuration;

import com.github.yannicklamprecht.ue2.messenger.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ysl3000
 */
public class NetworkConfiguration implements Configuration<NetworkConfiguration, Message> {

    private Socket out;
    private ServerSocket in;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    public NetworkConfiguration(int id) throws IOException {
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

    public InetAddress getAddress() {
        return in.getInetAddress();
    }

    public int getPort() {
        return in.getLocalPort();
    }

    @Override
    public void setNext(NetworkConfiguration next) {
        try {
            this.out = new Socket(next.getAddress(), next.getPort());
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
