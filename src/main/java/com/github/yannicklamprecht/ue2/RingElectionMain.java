package com.github.yannicklamprecht.ue2;

import com.github.yannicklamprecht.ue2.messenger.Message;
import com.github.yannicklamprecht.ue2.messenger.RingElement;
import com.github.yannicklamprecht.ue2.messenger.configuration.Configuration;
import com.github.yannicklamprecht.ue2.messenger.configuration.UDPNetworkConfiguration;
import com.github.yannicklamprecht.ue2.util.RingList;
import com.github.yannicklamprecht.ue2.util.ThrowableFunction;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by ysl3000
 */
public class RingElectionMain<T extends Configuration<T, Message>> {

    private RingList<RingElement<T>> ringElements = RingList.create();

    private RingElectionMain(ThrowableFunction<Integer,T, IOException> ringType, int size) {

        IntStream.range(0, size).forEachOrdered(i -> {
            try {
                ringElements.add(new RingElement<>(i,ringType.provide(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ringElements.forEach(RingElement::connectNextRingElement);
        ringElements.pickRandomOne().beginElection();

        try {
            executeParallel(ringElements);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ringElements.forEach(RingElement::print);
    }

    public static void main(String[] args) {
        int size = 5;

        new RingElectionMain<>(UDPNetworkConfiguration::new, size);
    }

    private void executeParallel(RingList<? extends Runnable> runnables) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        runnables.forEach(es::execute);
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
    }

}
