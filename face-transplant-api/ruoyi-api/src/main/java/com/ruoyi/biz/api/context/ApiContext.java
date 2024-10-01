package com.ruoyi.biz.api.context;


import com.ruoyi.biz.domain.User;

import java.util.Optional;

/**
 * @author Fly
 */
public class ApiContext {

    private static final ThreadLocal<User> USER_CONTEXT = new InheritableThreadLocal<>();

    private static final ThreadLocal<Long> REQUEST_COST_CONTEXT = new InheritableThreadLocal<>();

    private ApiContext() {
    }

    public static void setUser(User user) {
        USER_CONTEXT.set(user);
    }

    public static User getUser() {
        return USER_CONTEXT.get();
    }

    public static void removeUser() {
        USER_CONTEXT.remove();
    }

    public static void setRequestStartTime(Long startTime) {
        REQUEST_COST_CONTEXT.set(startTime);
    }

    public static void removeRequestStartTime() {
        REQUEST_COST_CONTEXT.remove();
    }

    public static Long getRequestStartTime() {
        return REQUEST_COST_CONTEXT.get();
    }

    public static Long getCurrentUserId() {
        return Optional.ofNullable(USER_CONTEXT.get()).map(User::getId).orElse(null);
    }

}
