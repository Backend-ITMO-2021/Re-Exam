package com.sb.ifmo.reexam.data;

import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser user;

    public Message(String text, Room room, CustomUser user) {
        this.text = text;
        this.time = LocalDateTime.now();
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("text", this.text);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        messageJSON.put("time", this.time.format(formatter));
        messageJSON.put("user", this.user);
        return messageJSON.toString();
    }
}
