package com.xiaozhi.common;

/**
 * @author 20232
 * 基于ThreadLocal的工具类，用于获取用户的id，只能用于同一个线程
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
