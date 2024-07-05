package com.esprit.gestiondesconges;

import org.springframework.stereotype.Component;

@Component
public class UserContexte {

    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void setUsername(String username) {
        userThreadLocal.set(username);
    }

    public static String getUsername() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
