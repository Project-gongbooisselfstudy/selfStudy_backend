package com.selfStudy_backend.domain;

import org.springframework.context.annotation.Configuration;
@Configuration
public class CookieUser {
    private static String g_id;
    private static String g_name;

    public static String getG_id() {
        return g_id;
    }

    public static void setG_id(String g_id) {
        CookieUser.g_id = g_id;
    }

    public static String getG_name() {
        return g_name;
    }

    public static void setG_name(String g_name) {
        CookieUser.g_name = g_name;
    }
}
