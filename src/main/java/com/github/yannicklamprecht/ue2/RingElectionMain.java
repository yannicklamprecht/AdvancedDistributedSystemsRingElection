package com.github.yannicklamprecht.ue2;

import com.github.yannicklamprecht.ue2.factory.RingElementFactory;
import com.github.yannicklamprecht.ue2.factory.RingType;
import com.github.yannicklamprecht.ue2.list.RingList;
import com.github.yannicklamprecht.ue2.ring.RingElement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by ysl3000
 */
public class RingElectionMain {

    private final int SIZE = 5;
    private RingList<RingElement> ringElements = RingList.create();

    private RingElectionMain() {

        RingElementFactory ringElementFactory = new RingElementFactory();

        IntStream.range(0,SIZE).forEachOrdered(i -> ringElements.add(ringElementFactory.create(i, RingType.NETWORK)));

        ringElements.forEach(RingElement::connectNextRingElement);
        ringElements.pickRandomOne().beginElection();

        try {
            executeParallel(ringElements);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ringElements.forEach(RingElement::print);
    }

    private void executeParallel(RingList<? extends Runnable> runnables) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        runnables.forEach(es::execute);
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        new RingElectionMain();
    }

}
