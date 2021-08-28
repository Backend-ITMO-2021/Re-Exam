package com.sb.ifmo.reexam.data;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private CustomUser admin;

    @ManyToMany(mappedBy = "joinedRooms")
    private Set<CustomUser> users;

    @OneToMany(mappedBy = "room")
    private Set<Message> messages;

    public Room(String name, boolean isPrivate, CustomUser admin) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.admin = admin;
        this.users = new HashSet<>();
        this.messages = new HashSet<>();
    }

    public Room() {
        this("", false, null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // strange naming for jba repository to correctly build "findByIsPrivate" method SQL query
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomUser getAdmin() {
        return admin;
    }

    public void setAdmin(CustomUser admin) {
        this.admin = admin;
    }

    public Set<CustomUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CustomUser> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public boolean isAvailabe(CustomUser principal) {
        if (!this.isPrivate) {
            return true;
        }
        if (this.admin == principal) {
            return true;
        }
        if (this.users.contains(principal)) {
            return true;
        }
        return false;
    }

    public void addUser(CustomUser newUser) {
        this.users.add(newUser);
    }

    @Override
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("id", this.id);
        response.put("isPrivate", this.isPrivate);
        response.put("name", this.name);
        response.put("admin", this.admin);
        JSONArray usersJSON = new JSONArray();
        for (CustomUser user : this.users) {
            usersJSON.put(user);
        }
        response.put("users", usersJSON);
        response.put("messages", this.messages);
        return response.toString();
    }
}
