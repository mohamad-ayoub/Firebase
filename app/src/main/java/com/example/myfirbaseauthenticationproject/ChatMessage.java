package com.example.myfirbaseauthenticationproject;

import java.util.Date;

public class ChatMessage {
    private Date date;
    private String userId;
    private String name;
    private String text;

    public ChatMessage(Date date, String userId, String name, String text) {
        this.date = date;
        this.userId = userId;
        this.name = name;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
