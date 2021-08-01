package com.sb.ifmodemo.demo.data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "games")
@EntityListeners(AuditingEntityListener.class)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "player1", nullable = false)
    private long player1;

    @Column(name = "player2", nullable = false)
    private long player2;

    @Column(name = "field", nullable = false)
    private String field;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Column(name = "winner")
    private String winner;

    @Column(name = "turns")
    private int turns;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayer1() {
        return player1;
    }

    public void setPlayer1(long player1) {
        this.player1 = player1;
    }

    public long getPlayer2() {
        return player2;
    }

    public void setPlayer2(long player2) {
        this.player2 = player2;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
}
