package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="league_seasons")
public class LeagueSeason {

    @Id
    @Column(name="name")
    private String name;

    @Column(name="end_year")
    @NotNull
    private int year;

    @Column(name="division")
    @NotNull
    private int division;

    public LeagueSeason() {}

    public LeagueSeason(String name, int year, int division) {
        this.name = name;
        this.year = year;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "LeagueSeason{" +
                "name='" + name + '\'' +
                ", end_year=" + year +
                ", division='" + division +
                '}';
    }
}