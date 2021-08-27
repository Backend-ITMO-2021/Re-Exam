package com.dzeru.springchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private Timestamp tod;


    @ManyToOne()
    private User sender;

    @JsonIgnore
    @ManyToOne()
    private Room room;

    public Timestamp getTod() {
        return tod;
    }

    public User getSender() {
        return sender;
    }

    public Room getRoom() {
        return room;
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

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
