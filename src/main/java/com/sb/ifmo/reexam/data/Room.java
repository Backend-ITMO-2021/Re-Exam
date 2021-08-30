package com.sb.ifmo.reexam.data;

import org.hibernate.mapping.Collection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private CustomUser admin;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CustomUser> users;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Message> messages;

    public Room(String name, boolean isPrivate, CustomUser admin) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.admin = admin;
        this.users = Collections.singleton(admin);
        this.messages = new ArrayList<>();
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public boolean isAvailabe(CustomUser principal) {
        if (!this.isPrivate) {
            return true;
        }
        return this.users.contains(principal);
    }

    public void addUser(CustomUser newUser) {
        this.users.add(newUser);
    }

    @Override
    public String toString() {
        JSONObject roomJSON = new JSONObject();
        roomJSON.put("id", this.id);
        roomJSON.put("isPrivate", this.isPrivate);
        roomJSON.put("name", this.name);
        return roomJSON.toString();
    }

    public String toStringFull() {
        JSONObject roomJSON = new JSONObject();
        roomJSON.put("id", this.id);
        roomJSON.put("isPrivate", this.isPrivate);
        roomJSON.put("name", this.name);
        roomJSON.put("admin", this.admin);
        JSONArray usersJSON = new JSONArray();
        for (CustomUser user : this.users) {
            usersJSON.put(user);
        }
        roomJSON.put("users", usersJSON);
        Collections.sort(this.messages);
        roomJSON.put("messages", messages.stream().map(Message::toString).collect(Collectors.toList()));
        return roomJSON.toString();
    }
}
