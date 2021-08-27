package com.dzeru.springchat.dto;

import com.dzeru.springchat.entities.Message;

import java.util.List;

public class RoomMessagesDto {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public RoomMessagesDto(List<Message> messages) {
        this.messages = messages;
    }
}
