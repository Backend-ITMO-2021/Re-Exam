package com.dzeru.springchat.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private boolean restricted = false; //private room

//    private Long userId;

    @ManyToOne()
    private User user;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean isRestricted() {
        return restricted;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "rooms")
    private Set<User> participants = new HashSet<>();


    public void setName(String name) {
        this.name = name;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    //    public void setParticipants(List<User> participants) {
//        this.participants = participants;
//    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {

    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set<Message> messages;
}
