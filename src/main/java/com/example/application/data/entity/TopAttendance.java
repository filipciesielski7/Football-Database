package com.example.application.data.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Immutable
@Table(name="top_attendance")
public class TopAttendance {

    @Id
    @Column(name="match_id")
    private Integer matchId;

    @Column(name="stadium_name")
    @Size(max = 100)
    @NotEmpty
    private String stadiumName;

    @Column(name="fans_number")
    @Max(value = 1000000)
    private int fansNumber;

    @Column(name="match_date")
    @Temporal(TemporalType.DATE)
    private Date matchDate;

    @Column(name="home_team")
    @Size(max = 40)
    @NotEmpty
    private String homeTeam;

    @Column(name="away_team")
    @Size(max = 40)
    @NotEmpty
    private String awayTeam;

    @Column(name="league")
    @Size(max = 100)
    private String league;

    public TopAttendance() {
    }

    public TopAttendance(Integer matchId, String stadiumName, int fansNumber, Date matchDate, String homeTeam, String awayTeam, String league) {
        this.matchId = matchId;
        this.stadiumName = stadiumName;
        this.fansNumber = fansNumber;
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.league = league;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "TopAttendance{" +
                "stadiumName='" + stadiumName + '\'' +
                ", fansNumber=" + fansNumber +
                ", matchDate=" + matchDate +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", league='" + league + '\'' +
                '}';
    }
}
