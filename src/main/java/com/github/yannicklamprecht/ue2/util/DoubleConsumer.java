package com.github.yannicklamprecht.ue2.util;

/**
 * Created by ysl3000
 */
@FunctionalInterface
public interface DoubleConsumer<T> {
    void consume(T current, T next);
}
