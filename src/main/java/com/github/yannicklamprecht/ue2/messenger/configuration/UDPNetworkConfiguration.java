package com.github.yannicklamprecht.ue2.messenger.configuration;

import com.github.yannicklamprecht.ue2.messenger.Message;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by ysl3000
 */
public class UDPNetworkConfiguration implements Configuration<UDPNetworkConfiguration, Message> {

    private DatagramSocket datagramSocket;
    private SocketAddress next;


    public UDPNetworkConfiguration(int id) {

        try {
            datagramSocket = new DatagramSocket(2000 + id, InetAddress.getLocalHost());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setNext(UDPNetworkConfiguration next) {
        this.next = next.datagramSocket.getLocalSocketAddress();
    }

    @Override
    public Message read() {

        byte[] data = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);

        try {
            datagramSocket.receive(datagramPacket);

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));

            return (Message) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void write(Message object) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, next);

            datagramSocket.send(datagramPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
