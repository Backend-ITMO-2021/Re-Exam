package com.chatty.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User sender;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    public Message(String content) {
        this.content = content;
    }

    public Message() {

    }
}
