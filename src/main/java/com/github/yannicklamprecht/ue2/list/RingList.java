package com.github.yannicklamprecht.ue2.list;

import java.util.List;

/**
 * Created by ysl3000
 */
public interface RingList<T> extends List<T> {
    /**
     *
     * @param consumer
     */
    void forEach(DoubleConsumer<T> consumer);

    T pickRandomOne();
}