package com.dzeru.springchat.models;

import java.sql.Timestamp;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private Timestamp tod;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setTod(Timestamp tod) {
        this.tod = tod;
    }

    public Timestamp getTod() {
        return tod;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
