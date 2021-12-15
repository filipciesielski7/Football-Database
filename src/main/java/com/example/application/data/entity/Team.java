package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="teams")
public class Team {

    @Id
    @Column(name="name")
    @Size(max = 40)
    @NotEmpty
    private String name;

    @Column(name="city")
    @Size(max = 40)
    @NotEmpty
    private String city;

    @ManyToOne
    @JoinColumn(name = "stadium_name")
    @Nullable
    private Stadium stadium;

    @Column(name="wins", columnDefinition = "integer default 0")
    @Max(value=10000)
    private Integer wins = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<ClubEmployee> clubEmployees = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homeTeam", cascade = CascadeType.REMOVE)
    private List<Match> homeMatches = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "awayTeam", cascade = CascadeType.REMOVE)
    private List<Match> awayMatches = new LinkedList<>();

    @PreRemove
    public void nullificationClubEmployees() {
        clubEmployees.forEach(clubEmployee -> clubEmployee.setTeam(null));
    }

    public Team() {
    }

    public Team (String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String City) {
        this.city = City;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public List<ClubEmployee> getClubEmployees() {
        return clubEmployees;
    }

    public void setClubEmployees(List<ClubEmployee> clubEmployees) {
        this.clubEmployees = clubEmployees;
    }

    public List<Match> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<Match> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<Match> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<Match> awayMatches) {
        this.awayMatches = awayMatches;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}