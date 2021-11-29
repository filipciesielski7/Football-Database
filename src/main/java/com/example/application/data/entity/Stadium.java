package com.example.application.data.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="stadiums")
public class Stadium {

    @Id
    @Column(name="name")
    @NotEmpty
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stadium")
    private List<Team> teams = new LinkedList<>();

    @Column(name="capacity")
    @NotNull
    private int capacity;

    @Column(name="has_lightning")
    @NotNull
    private String hasLightning;

    @Column(name="has_under_soil_heating")
    @NotNull
    private String hasUnderSoilHeating;

    @PreRemove
    public void nullificationTeams() {
        teams.forEach(team -> team.setStadium(null));
    }

    public Stadium() {
    }

    public Stadium (String name, int capacity, String hasLightning, String hasUnderSoilHeating) {
        this.name = name;
        this.capacity = capacity;
        this.hasLightning = hasLightning;
        this.hasUnderSoilHeating = hasUnderSoilHeating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getHasLightning() {
        return hasLightning;
    }

    public void setHasLightning(String hasLightning) {
        this.hasLightning = hasLightning;
    }

    public String getHasUnderSoilHeating() {
        return hasUnderSoilHeating;
    }

    public void setHasUnderSoilHeating(String hasUnderSoilHeating) {
        this.hasUnderSoilHeating = hasUnderSoilHeating;
    }

    @Override
    public String toString() {
        return "Stadiums{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", hasLightning='" + hasLightning + '\'' +
                ", hasUndersoilHeating='" + hasUnderSoilHeating + '\'' +
                '}';
    }
}
