package com.sb.ifmo.reexam.data;

import org.json.JSONObject;

public class MessagesTop {
    private String username;
    private long count;

    public MessagesTop(String username, long count) {
        this.username = username;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        JSONObject messagesTopJSON = new JSONObject();
        messagesTopJSON.put("username", this.username);
        messagesTopJSON.put("count", this.count);
        return messagesTopJSON.toString();
    }
}
