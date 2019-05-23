package com.github.yannicklamprecht.ue2.messenger;

import java.util.Optional;

public interface Messenger<T,DTO> extends Reader<DTO>, Writer<DTO>{
    default Optional<T> getConfigurator(){return Optional.empty();}
    default boolean init(){
        return true;
    }
}
