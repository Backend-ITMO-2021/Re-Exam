package com.sb.ifmo.reexam.data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private CustomUser admin;

    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToMany(mappedBy = "joinedRooms")
    private Set<CustomUser> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getAdmin() {
        return admin;
    }

    public void setAdmin(CustomUser admin) {
        this.admin = admin;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<CustomUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CustomUser> users) {
        this.users = users;
    }
}
