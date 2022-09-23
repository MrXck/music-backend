package com.music.utils;

public class UserThreadLocal {

    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<>();

    private UserThreadLocal() {

    }

    /**
     * 将用户id放入本地线程中
     *
     * @param userId
     */
    public static void set(Integer userId) {
        LOCAL.set(userId);
    }

    /**
     * 从本地线程中获取当前的用户id
     *
     * @return
     */
    public static Integer get() {
        return LOCAL.get();
    }

    /**
     * 从本地线程中删除当前的用户id
     */
    public static void remove() {
        LOCAL.remove();
    }
}
