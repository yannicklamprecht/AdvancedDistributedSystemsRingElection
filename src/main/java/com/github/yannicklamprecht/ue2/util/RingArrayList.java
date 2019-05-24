package com.github.yannicklamprecht.ue2.util;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ysl3000
 */
public class RingArrayList<T> extends ArrayList<T> implements RingList<T> {

    @Override
    public void forEach(DoubleConsumer<T> consumer) {
        for (int i = 0; i < size(); i++) {
            consumer.consume(get(i), get((i + 1) % size()));
        }
    }

    @Override
    public T pickRandomOne() {
        return get(ThreadLocalRandom.current().nextInt(size()));
    }
}
