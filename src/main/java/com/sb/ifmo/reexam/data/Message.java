package com.sb.ifmo.reexam.data;

import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser user;

    public Message(String text, Room room, CustomUser user) {
        this.text = text;
        this.room = room;
        this.user = user;
    }

    public Message() {
        this("", null, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("id", this.id);
        response.put("text", this.text);
        response.put("room_id", this.room.getId());
        response.put("user", this.user);
        return response.toString();
    }
}
