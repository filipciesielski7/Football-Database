package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Team {
    @Id
    private String Name = "";

    @NotEmpty
    private String City = "";

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    @NotNull
    @JsonIgnoreProperties({"teams"})
    private Stadium stadium;

    @Override
    public String toString() {
        return Name + " from " + City;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setCompany(Stadium stadium) {
        this.stadium = stadium;
    }
}
