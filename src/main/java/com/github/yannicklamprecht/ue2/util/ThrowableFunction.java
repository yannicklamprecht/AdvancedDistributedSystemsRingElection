package com.github.yannicklamprecht.ue2.util;

/**
 * Created by ysl3000
 */
public interface ThrowableFunction<P,R,T extends Throwable> {
    R provide(P p) throws T;
}
