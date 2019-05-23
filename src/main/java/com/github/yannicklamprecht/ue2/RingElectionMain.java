package com.github.yannicklamprecht.ue2;

import com.github.yannicklamprecht.ue2.factory.RingElementFactory;
import com.github.yannicklamprecht.ue2.factory.RingType;
import com.github.yannicklamprecht.ue2.list.RingArrayList;
import com.github.yannicklamprecht.ue2.list.RingList;
import com.github.yannicklamprecht.ue2.ring.RingElement;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ysl3000
 */
public class RingElectionMain {

    private final int size = 5;
    RingList<RingElement> ringElements = new RingArrayList<>();

    public RingElectionMain() throws IOException {

        RingElementFactory ringElementFactory = new RingElementFactory();

        for (int i = 0; i < size; i++) {
            ringElements.add(ringElementFactory.create(i, RingType.NETWORK));
        }

        ringElements.forEach((RingElement::connectNextRingElement));
        ringElements.pickRandomOne().beginElection();

        try {
            executeParallel(ringElements);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ringElements.forEach(RingElement::print);

    }

    public static void main(String[] args) {
        try {
            new RingElectionMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeParallel(List<? extends Runnable> runnables) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        runnables.forEach(es::execute);
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
    }

}
