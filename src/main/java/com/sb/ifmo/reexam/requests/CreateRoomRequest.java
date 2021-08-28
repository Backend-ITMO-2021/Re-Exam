package com.sb.ifmo.reexam.requests;

public class CreateRoomRequest {
    private String name;
    private boolean isPrivate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
