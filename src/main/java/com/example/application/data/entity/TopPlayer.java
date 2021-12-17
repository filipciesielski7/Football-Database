package com.example.application.data.entity;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Immutable
@Table(name="top_players_ranking")
public class TopPlayer {

    @Id
    @Column(name="pesel")
    @Size(min = 11, message = "wielkość musi być równa 11")
    @Size(max = 11, message = "wielkość musi być równa 11")
    @Pattern(regexp="^[0-9]*$", message = "pesel może składać się tylko z cyfr")
    private String pesel;

    @Column(name="first_name")
    @Size(max = 20)
    private String firstName;

    @Column(name="last_name")
    @Size(max = 30)
    private String lastName;

    @Column(name="goals")
    @Min(value = 0)
    private int goals;

    @Column(name="assists")
    @Min(value = 0)
    private int assists;

    @Column(name="team_name")
    @Size(max = 40)
    @NotEmpty
    private String teamName;

    @Column(name="league")
    @Size(max = 100)
    private String league;

    public TopPlayer() {
    }

    public TopPlayer(String pesel, String firstName, String lastName, int goals, int assists, String teamName, String league) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.goals = goals;
        this.assists = assists;
        this.teamName = teamName;
        this.league = league;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "TopPlayer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", goals=" + goals +
                ", assists=" + assists +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
