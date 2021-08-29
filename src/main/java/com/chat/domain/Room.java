package com.chat.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author
 */
@Entity
@Table(name = "chat_table")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userAdmin;

    private String title;

    private Boolean isPrivate;

    @ManyToMany(mappedBy = "rooms", cascade = {CascadeType.ALL})
    private List<User> users;

    public Room(String title, User userAdmin, List<User> users) {
        this.title = title;
        this.userAdmin = userAdmin;
        this.users = users;
        this.isPrivate = false;
    }

    public Room() {
    }

    public void addUser(User user) {
        users.add(user);
        user.getRooms().add(this);
    }

    public Long getId() {
        return id;
    }

    public User getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(User userAdmin) {
        this.userAdmin = userAdmin;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrivate() {
        return Optional.ofNullable(isPrivate).orElse(false);
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
