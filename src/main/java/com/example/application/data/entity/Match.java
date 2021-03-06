package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="matches")
public class Match {

    @Id
    @GeneratedValue(generator="match_seq")
    @SequenceGenerator(name="match_seq", sequenceName = "MATCH_SEQ", allocationSize = 1)
    @Column(name="match_id")
    private Integer matchId;

    @Column(name="match_date")
    @Temporal(TemporalType.DATE)
    private Date matchDate;

    @Column(name="goals_home_team")
    @Min(value = 0)
    @Max(value = 1000)
    private int homeGoals;

    @Column(name="goals_away_team")
    @Min(value = 0)
    @Max(value = 1000)
    private int awayGoals;

    @Column(name="fans_number")
    @Max(value = 1000000)
    private int fansNumber;

    @ManyToOne
    @JoinColumn(name = "home_team")
    @NotNull
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team")
    @NotNull
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "stadium_name")
    @NotNull
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "league_year")
    @NotNull
    private LeagueSeason leagueSeason;

    @ManyToOne
    @JoinColumn(name = "delegate")
    @NotNull
    private LeagueEmployee leagueEmployee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchId", cascade = CascadeType.REMOVE)
    private List<Refereeing> refereeings = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchId", cascade = CascadeType.REMOVE)
    private List<Participating> participatings = new LinkedList<>();

    public Match() {
    }

    public Match(Integer matchId, Date matchDate, int homeGoals, int awayGoals, int fansNumber) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.fansNumber = fansNumber;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public LeagueSeason getLeagueSeason() {
        return leagueSeason;
    }

    public void setLeagueSeason(LeagueSeason leagueSeason) {
        this.leagueSeason = leagueSeason;
    }

    public LeagueEmployee getLeagueEmployee() {
        return leagueEmployee;
    }

    public void setLeagueEmployee(LeagueEmployee leagueEmployee) {
        this.leagueEmployee = leagueEmployee;
    }

    public String getMatchIdString() {
        return matchId.toString();
    }


    public List<Refereeing> getRefereeings() {
        return refereeings;
    }

    public void setRefereeings(List<Refereeing> refereeings) {
        this.refereeings = refereeings;
    }

    public List<Participating> getParticipatings() {
        return participatings;
    }

    public void setParticipatings(List<Participating> participatings) {
        this.participatings = participatings;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", matchDate=" + matchDate +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                '}';
    }

    public String matchInfo() {
        return matchId + ". " + homeTeam.getName() + " vs " + awayTeam.getName();
    }
}
