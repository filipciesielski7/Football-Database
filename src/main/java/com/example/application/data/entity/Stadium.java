package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Stadium {
    @Id
    private String Name = "";

    @OneToMany(mappedBy = "stadium")
    @Nullable
    private List<Team> teams = new LinkedList<>();

    @NotNull
    private int Capacity = 0;

    @NotEmpty
    private String hasLighting = "";

    @NotEmpty
    private String hasUnderSoilHeating = "";

    @Override
    public String toString() {
        return Name + " stadium with capacity of " + Capacity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        this.Capacity = capacity;
    }

    public String getHasLighting() {
        return hasLighting;
    }

    public void setHasLighting(String hasLighting) {
        this.hasLighting = hasLighting;
    }

    public String getHasUnderSoilHeating() {
        return hasUnderSoilHeating;
    }

    public void setHasUnderSoilHeating(String hasUnderSoilHeating) {
        this.hasUnderSoilHeating = hasUnderSoilHeating;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
