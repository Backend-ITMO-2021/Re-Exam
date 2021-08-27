package com.sb.ifmo.reexam.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
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
}
