package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="kluby")
public class Team {

    @Id
    @Column(name="nazwa")
    private String name = "";

    @NotEmpty
    @Column(name="miejscowosc")
    private String city = "";

    @ManyToOne
    @JoinColumn(name = "stadiony_nazwa")
    @NotNull
    private Stadium stadium;

    @Override
    public String toString() {
        return name + " from " + city;
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
}