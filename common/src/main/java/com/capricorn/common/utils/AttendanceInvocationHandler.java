package com.capricorn.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class AttendanceInvocationHandler implements InvocationHandler {

    private final Object target;

    public AttendanceInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();

        Object invoke = method.invoke(target, args);

        long end = System.currentTimeMillis();
        log.info(String.format("执行耗时：%s ms",end-start));
        return invoke;
    }
}
