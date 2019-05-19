package com.github.yannicklamprecht.ue2;

import com.github.yannicklamprecht.ue2.messenger.LocalBufferMessenger;
import com.github.yannicklamprecht.ue2.messenger.Messenger;
import com.github.yannicklamprecht.ue2.ring.RingElement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ysl3000
 */
public class RingElectionMain {

    private final int size = 5;
    List<RingElement> ringElements = new ArrayList<>();

    public RingElectionMain() {

        for (int i = 0; i < size; i++) {
            Messenger messenger = new LocalBufferMessenger();
            RingElement ringElement = new RingElement(i,messenger);
            ringElements.add(ringElement);
        }

        int rand = ThreadLocalRandom.current().nextInt(ringElements.size());

        ringElements.get(rand).beginElection();

        for(int i=0; i< ringElements.size(); i++){
            RingElement current = ringElements.get(i);
            RingElement next = ringElements.get((i+1)%ringElements.size());
            current.setReader(next.getWriter());
            current.start();
        }

        for (Thread thread : ringElements) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ringElements.forEach(RingElement::getElectedID);



    }

    public static void main(String[] args) {
        new RingElectionMain();
    }
}
