package com.example.application.data.entity;

import com.example.application.data.entity.helper.ParticipatingId;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@IdClass(ParticipatingId.class)
@Table(name = "participating")
public class Participating implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pesel")
    private ClubEmployee pesel;

    @Id
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match matchId;

    @Column(name = "goals")
    @Min(value = 0)
    @Max(value = 1000)
    private int goals;

    @Column(name = "assists")
    @Min(value = 0)
    @Max(value = 1000)
    private int assists;

    @Column(name="got_yellow_card")
    @NotEmpty
    @NotNull
    private String gotYellowCard;

    @Column(name="got_red_card")
    @NotEmpty
    @NotNull
    private String gotRedCard;

    public Participating() {
    }

    public Participating(int goals, int assists, String gotYellowCard, String gotRedCard) {
        this.goals = goals;
        this.assists = assists;
        this.gotYellowCard = gotYellowCard;
        this.gotRedCard = gotRedCard;
    }

    public ClubEmployee getPesel() {
        return pesel;
    }

    public void setPesel(ClubEmployee pesel) {
        this.pesel = pesel;
    }

    public Match getMatchId() {
        return matchId;
    }

    public void setMatchId(Match matchId) {
        this.matchId = matchId;
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

    public String getGotYellowCard() {
        return gotYellowCard;
    }

    public void setGotYellowCard(String gotYellowCard) {
        this.gotYellowCard = gotYellowCard;
    }

    public String getGotRedCard() {
        return gotRedCard;
    }

    public void setGotRedCard(String gotRedCard) {
        this.gotRedCard = gotRedCard;
    }

    @Override
    public String toString() {
        return "Participating{" +
                "pesel=" + pesel +
                ", matchId=" + matchId +
                ", goals=" + goals +
                ", assists=" + assists +
                ", gotYellowCard='" + gotYellowCard + '\'' +
                ", gotRedCard='" + gotRedCard + '\'' +
                '}';
    }
}
