package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Immutable
@Table(name="full_ranking")
public class Ranking {

    @Id
    @Column(name="name")
    @Size(max = 40)
    private String name;

    @Column(name="points")
    @Min(value = 0)
    private int points;

    @Column(name="wins")
    @Min(value = 0)
    private int wins;

    @Column(name="draws")
    @Min(value = 0)
    private int draws;

    @Column(name="loses")
    @Min(value = 0)
    private int loses;

    @Column(name="scored")
    @Min(value = 0)
    private int scored;

    @Column(name="conceded")
    @Min(value = 0)
    private int conceded;

    @Column(name="league")
    @Size(max = 100)
    private String league;

    public Ranking() {
    }

    public Ranking(String name, int points, int wins, int draws, int loses, int scored, int conceded, String league) {
        this.name = name;
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.scored = scored;
        this.conceded = conceded;
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getConceded() {
        return conceded;
    }

    public void setConceded(int conceded) {
        this.conceded = conceded;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", league='" + league + '\'' +
                '}';
    }
}
