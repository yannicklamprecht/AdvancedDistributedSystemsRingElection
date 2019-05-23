package com.github.yannicklamprecht.ue2.ring;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.message.MessageType;
import com.github.yannicklamprecht.ue2.messenger.Messenger;
import com.github.yannicklamprecht.ue2.messenger.Settable;

/**
 * Created by ysl3000
 */
public abstract class RingElement<T extends Settable<T>> implements Runnable {

    private final Messenger<T, Message> messenger;
    private Participation participation = Participation.NONE;
    private int id;
    private Integer electedID;

    public RingElement(int id, Messenger<T, Message> messenger) {
        this.id = id;
        this.messenger = messenger;
    }

    public Messenger<T, Message> getMessenger() {
        return messenger;
    }

    public void connectToNextMessenger(T networkConfiguration) {
        this.getMessenger().getConfigurator().ifPresent(current -> current.setNext(networkConfiguration));
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        messenger.init();

        while (electedID == null) {
            Message message = messenger.read();
            if (message == null) {
                continue;
            }
            System.out.println(message);

            switch (message.getMessageType()) {
                case ELECTED:
                    onElectedMessage(message);
                    break;
                case ELECTION:
                    onElectionMessage(message);
                    break;
            }
        }
    }

    public Integer getElectedID() {
        return electedID;
    }

    private void onElectedMessage(Message message) {
        if (message.getId() != id) {
            this.participation = Participation.NONE;
            this.electedID = message.getId();
            messenger.write(message);
        } else {
            this.electedID = id;
            System.out.println("Election is over! Elected is " + id);
        }
    }

    private void onElectionMessage(Message message) {
        System.out.println("Own id: " + id + " messageId: " + message.getId());
        if (id < message.getId()) {
            messenger.write(message);
            this.participation = Participation.PARTICIPATING;
        } else if (id > message.getId()) {
            if (participation == Participation.NONE) {
                message.setId(id);
                messenger.write(message);
                this.participation = Participation.PARTICIPATING;
            }
        } else {
            participation = Participation.NONE;
            messenger.write(new Message(MessageType.ELECTED, id));
            System.out.println("Process " + id + " acting as a leader");
        }
    }


    public void beginElection() {
        messenger.write(new Message(MessageType.ELECTION, id));
        this.participation = Participation.PARTICIPATING;
    }


    public Participation getParticipation() {
        return participation;
    }


    public abstract void connectNextRingElement(RingElement<T> ringElement);

    @Override
    public String toString() {
        return "RingElement{" +
        "messenger=" + messenger +
        ", participation=" + participation +
        ", id=" + id +
        ", electedID=" + electedID +
        '}';
    }

    public void print(){
        System.out.println("RingElement: " + getId() + " ParticipationStatus: " + getParticipation() + " electedId " + getElectedID());
    }

}
