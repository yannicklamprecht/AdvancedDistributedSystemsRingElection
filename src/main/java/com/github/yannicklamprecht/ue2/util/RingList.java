package com.github.yannicklamprecht.ue2.util;

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

    static <T> RingList<T> create(){
        return new RingArrayList<>();
    }
}
