package com.selfStudy_backend.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public class User {
    private String g_id;
    private String g_name;



    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }
}
