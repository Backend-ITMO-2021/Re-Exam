package com.chatty.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String name;

    private boolean isPublic;

    @NotNull
    @OneToOne
    private User owner;

    @OneToMany(mappedBy = "room")
    private Set<UserRoom> participants = new HashSet<>();

    @OneToMany(mappedBy = "room")
    private Set<Message> messages = new HashSet<>();
}
