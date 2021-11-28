package com.example.application.data.entity;

import com.sun.istack.NotNull;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="stadiony")
public class Stadium {

    @Id
    @Column(name="nazwa")
    private String name;

    @OneToMany(mappedBy = "stadium")
    @Nullable
    private List<Team> teams = new LinkedList<>();

    @NotNull
    @Column(name="pojemnosc")
    private int capacity;

    @NotNull
    @Column(name="czy_oswietlenie")
    private String hasLighting;

    @NotNull
    @Column(name="czy_podgrzewana_murawa")
    private String hasUnderSoilHeating;

    public Stadium() {
    }

    public Stadium(String name, int capacity, String hasLightning, String hasUndersoilHeating) {
        this.name = name;
        this.capacity = capacity;
        this.hasLighting = hasLightning;
        this.hasUnderSoilHeating = hasUndersoilHeating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getHasLighting() {
        return hasLighting;
    }

    public void setHasLighting(String hasLightning) {
        this.hasLighting = hasLightning;
    }

    public String getHasUnderSoilHeating() {
        return hasUnderSoilHeating;
    }

    public void setHasUnderSoilHeating(String hasUndersoilHeating) {
        this.hasUnderSoilHeating = hasUndersoilHeating;
    }

    @Override
    public String toString() {
        return "Stadiums{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", hasLightning='" + hasLighting + '\'' +
                ", hasUndersoilHeating='" + hasUnderSoilHeating + '\'' +
                '}';
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}