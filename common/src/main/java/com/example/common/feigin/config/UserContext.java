package com.example.common.feigin.config;

/**
 * UserContext<br>
 * <p>
 * 作成日：2026/9/11<br>
 * 作成者：秦振兴<br>
 */
public class UserContext {
    private static final ThreadLocal<String> USER_ID_HOLDER = new ThreadLocal<>();

    public static void setUserId(String userId) {
        USER_ID_HOLDER.set(userId);
    }

    public static String getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void clear() {
        USER_ID_HOLDER.remove(); // 一定要 remove，避免内存泄漏和线程池复用导致的脏数据
    }
}
