package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Stadium {
    @Id
    private String Name = "";

    @OneToMany(mappedBy = "stadium")
    @Nullable
    private List<Team> teams = new LinkedList<>();

    @NotEmpty
    private int Capacity = 0;

    @NotEmpty
    private boolean hasLighting = false;

    @NotEmpty
    private boolean hasUnderSoilHeating = false;

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
        Capacity = capacity;
    }

    public boolean getHasLighting() {
        return hasLighting;
    }

    public void setHasLighting(boolean hasLighting) {
        this.hasLighting = hasLighting;
    }

    public boolean getHasUnderSoilHeating() {
        return hasUnderSoilHeating;
    }

    public void setHasUnderSoilHeating(boolean hasUnderSoilHeating) {
        this.hasUnderSoilHeating = hasUnderSoilHeating;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
