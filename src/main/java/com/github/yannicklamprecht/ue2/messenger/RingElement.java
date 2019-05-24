package com.github.yannicklamprecht.ue2.messenger;

import com.github.yannicklamprecht.ue2.messenger.configuration.Configuration;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by ysl3000
 */
public class RingElement<T extends Configuration<T, Message>> implements Runnable {

    private final T messenger;
    private Participation participation = Participation.NONE;
    private int id;
    private Integer electedID;

    private Map<MessageType, Consumer<Message>> action = Map.of(MessageType.ELECTED, this::onElectedMessage, MessageType.ELECTION, this::onElectionMessage);

    public RingElement(int id, T messenger) {
        this.id = id;
        this.messenger = messenger;
    }

    public void connectNextRingElement(RingElement<T> ringElement) {
        this.messenger.setNext(ringElement.messenger);
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
            action.get(message.getMessageType()).accept(message);
        }
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


    @Override
    public String toString() {
        return "RingElement{" +
        "messenger=" + messenger +
        ", participation=" + participation +
        ", id=" + id +
        ", electedID=" + electedID +
        '}';
    }

    public void print() {
        System.out.println("RingElement: " + id + " ParticipationStatus: " + participation + " electedId " + electedID);
    }

}
