package com.github.yannicklamprecht.ue2.messenger.network;

import com.github.yannicklamprecht.ue2.messenger.Initable;
import com.github.yannicklamprecht.ue2.messenger.Settable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

/**
 * Created by ysl3000
 */
public class NetworkConfiguration implements Settable<NetworkConfiguration>, Initable {

    private Socket out;
    private ServerSocket in;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    public NetworkConfiguration(ServerSocket in) {
        this.in = in;
    }

    public ObjectInputStream getIn() {
        return objectInputStream;
    }

    public Optional<ObjectOutputStream> getOut() {

        if (objectOutputStream == null && out!= null) {

            try {
                this.objectOutputStream = new ObjectOutputStream(out.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(objectOutputStream);
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

    public boolean init(){
        if (objectInputStream == null) {

            try {
                Socket socket = in.accept();
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
