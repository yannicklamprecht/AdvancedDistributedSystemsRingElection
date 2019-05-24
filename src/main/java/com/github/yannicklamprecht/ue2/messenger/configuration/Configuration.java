package com.github.yannicklamprecht.ue2.messenger.configuration;

/**
 * Created by ysl3000
 */
public interface Configuration<T, M>{

    void setNext(T next);

    M read();

    void write(M object);

    default void init(){}
}
