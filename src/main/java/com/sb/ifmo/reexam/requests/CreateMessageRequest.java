package com.sb.ifmo.reexam.requests;

public class CreateMessageRequest {
    private String text;
    private long roomId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
