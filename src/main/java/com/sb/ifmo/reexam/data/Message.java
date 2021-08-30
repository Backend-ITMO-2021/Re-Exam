package com.sb.ifmo.reexam.data;

import org.json.JSONObject;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser user;

    public Message(String text, Room room, CustomUser user) {
        this.text = text;
            this.time = new Date();
        this.room = room;
        this.user = user;
    }

    public Message(String text, Room room, CustomUser user, Date time) {
        this.text = text;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
        messageJSON.put("id", this.id);
        messageJSON.put("text", this.text);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM HH:mm:ss");
        messageJSON.put("time", formatter.format(this.time));
        messageJSON.put("user", this.user);
        return messageJSON.toString();
    }

    @Override
    public int compareTo(Message other) {
        if (time == null || other.time == null) {
            return 0;
        }
        return time.compareTo(other.time);
    }
}
