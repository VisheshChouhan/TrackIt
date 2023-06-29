package com.hfad.login;


import java.util.Date;

public class ChatMessage
{
    String message;
    String user;
    Date timeStamp;
    public ChatMessage()
    {

    }

    public ChatMessage(String message, String user, Date timeStamp) {
        this.message = message;
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}


