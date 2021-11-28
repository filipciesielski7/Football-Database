package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="teams")
public class Team {

    @Id
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name="city")
    private String city;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "stadium_name")
    private Stadium stadium;

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

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}