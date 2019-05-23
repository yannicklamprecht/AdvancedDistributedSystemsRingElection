package com.github.yannicklamprecht.ue2.list;

/**
 * Created by ysl3000
 */
@FunctionalInterface
public interface DoubleConsumer<T> {
    void consume(T current, T next);
}
