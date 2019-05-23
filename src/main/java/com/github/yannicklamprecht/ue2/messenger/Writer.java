package com.github.yannicklamprecht.ue2.messenger;

/**
 * Created by ysl3000
 */
@FunctionalInterface
public interface Writer<T> {
    void write(T object);
}
