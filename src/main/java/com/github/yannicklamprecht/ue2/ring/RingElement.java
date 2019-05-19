package com.github.yannicklamprecht.ue2.ring;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.message.MessageType;
import com.github.yannicklamprecht.ue2.messenger.Messenger;

/**
 * Created by ysl3000
 */
public class RingElement extends Thread {

    private final Messenger writer;
    private Messenger reader;
    private Participation participation = Participation.NONE;
    private int id;
    private Integer electedID;

    public RingElement(int id, Messenger writer) {
        this.id = id;
        this.writer = writer;
    }

    public void setReader(Messenger reader) {
        this.reader = reader;
    }

    public Messenger getWriter() {
        return writer;
    }

    @Override
    public void run() {
        while (electedID == null) {
            Message message = reader.readMessage();
            if (message == null) {
                continue;
            }
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

    public Integer getElectedID(){
        return electedID;
    }

    private void onElectedMessage(Message message) {
        if (message.getId() != id) {
            this.participation = Participation.NONE;
            this.electedID = message.getId();
            writer.writeMessage(message);
        } else {
            this.electedID = id;
            System.out.println("Election is over! Elected is " + id);
        }
    }

    private void onElectionMessage(Message message) {
        System.out.println("Own id: "+ id + " messageId: "+ message.getId());
        if (id < message.getId()) {
            writer.writeMessage(message);
            this.participation = Participation.PARTICIPATING;
        } else if (id > message.getId()) {
            if (participation == Participation.NONE) {
                message.setId(id);
                writer.writeMessage(message);
                this.participation = Participation.PARTICIPATING;
            }
        } else {
            participation = Participation.NONE;
            writer.writeMessage(new Message(MessageType.ELECTED, id));
            System.out.println("Process " + id + " acting as a leader");
        }
    }


    public void beginElection() {
        writer.writeMessage(new Message(MessageType.ELECTION, id));
        this.participation = Participation.PARTICIPATING;
    }


    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }
}
