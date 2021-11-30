package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="teams")
public class Team {

    @Id
    @Column(name="name")
    @NotEmpty
    private String name;

    @Column(name="city")
    @NotEmpty
    private String city;

    @ManyToOne
    @JoinColumn(name = "stadium_name")
    @Nullable
    private Stadium stadium;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<ClubEmployee> clubEmployees = new LinkedList<>();

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

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}