package com.sb.ifmodemo.demo.data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "ifmo_users")
@EntityListeners(AuditingEntityListener.class)
public class IfmoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "session")
    private String session;

    @Column(name = "password")
    private String password;

    @Column(name = "game")
    private long game;

    public long getGame() {
        return game;
    }

    public void setGame(long game) {
        this.game = game;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
