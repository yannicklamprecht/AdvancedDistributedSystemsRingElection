package com.github.yannicklamprecht.ue2.list;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ysl3000
 */
public class RingArrayList<T> extends ArrayList<T> implements RingList<T> {

    @Override
    public void forEach(DoubleConsumer<T> consumer) {
        for (int i = 0; i < size(); i++) {
            T current = get(i);
            T next = get((i + 1) % size());
            consumer.consume(current, next);
        }
    }

    @Override
    public T pickRandomOne() {
        return get(ThreadLocalRandom.current().nextInt(size()));
    }
}
