package com.example.application.data.entity;

import com.example.application.data.entity.helper.RefereeingId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@IdClass(RefereeingId.class)
@Table(name="refereeing")
public class Refereeing implements Serializable {


    @Id
    @ManyToOne
    @JoinColumn(name = "pesel")
    private LeagueEmployee pesel;

    @Id
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match matchId;

    @Column(name="function")
    @Size(max = 30)
    @NotEmpty
    private String function;

    public Refereeing() {

    }


    public Refereeing(String function) {
        this.function = function;
    }

    public LeagueEmployee getPesel() {
        return pesel;
    }

    public void setPesel(LeagueEmployee pesel) {
        this.pesel = pesel;
    }

    public Match getMatchId() {
        return matchId;
    }

    public void setMatchId(Match matchId) {
        this.matchId = matchId;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "Refereeing{" +
                "pesel='" + pesel + '\'' +
                ", matchId=" + matchId +
                ", function='" + function + '\'' +
                '}';
    }
}
