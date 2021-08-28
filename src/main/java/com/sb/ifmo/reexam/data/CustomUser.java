package com.sb.ifmo.reexam.data;

import org.json.JSONObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class CustomUser implements OAuth2User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany
    private Set<Room> joinedRooms;

    @OneToMany(mappedBy = "admin")
    private Set<Room> ownedRooms;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages;

    public CustomUser(String username, String email) {
        super();
        this.username = username;
        this.email = email;
        this.joinedRooms = new HashSet<Room>();
        this.ownedRooms = new HashSet<Room>();
    }

    public CustomUser() {
        this("", "example@example.com");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public <A> A getAttribute(String name) {
        return (A) this.getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("id", this.getId());
        attributes.put("username", this.getUsername());
        attributes.put("email", this.getEmail());
        attributes.put("joinedRooms", this.getJoinedRooms());
        attributes.put("ownedRooms", this.getOwnedRooms());
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    public Set<Room> getJoinedRooms() {
        return joinedRooms;
    }

    public void setJoinedRooms(Set<Room> joinedRooms) {
        this.joinedRooms = joinedRooms;
    }

    public Set<Room> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(Set<Room> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("username", this.username);
        response.put("email", this.email);
        return response.toString();
    }
}
