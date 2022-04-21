package com.selfStudy_backend.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Scrap {

    private String user_id;
    private int question_id;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
