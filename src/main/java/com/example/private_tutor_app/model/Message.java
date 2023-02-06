package com.example.private_tutor_app.model;

public class Message {

    private String sender;
    private String Receiver;
    private String message;

    public Message() {
    }

    public Message(String sender, String receiver, String message) {
        this.sender = sender;
        Receiver = receiver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
