package com.github.yannicklamprecht.ue2.messenger;

/**
 * Created by ysl3000
 */
@FunctionalInterface
public interface Reader<T> {

    T read();
}
