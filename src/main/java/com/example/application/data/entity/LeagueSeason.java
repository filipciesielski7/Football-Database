package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="league_seasons")
public class LeagueSeason {

    @Id
    @Column(name="name")
    @Size(max = 100)
    @NotEmpty
    private String name;

    @Column(name="end_year")
    @Min(value = 1)
    @Max(value = 2022)
    @NotNull
    private int year;

    @Column(name="division")
    @Min(value = 1)
    @Max(value = 99)
    @NotNull
    private int division;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leagueSeason", cascade = CascadeType.REMOVE)
    private List<Match> matches = new LinkedList<>();

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

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
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