package com.example.application.data.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final StadiumRepository stadiumRepository;
    private final TeamRepository teamRepository;
    private final LeagueSeasonRepository leagueSeasonRepository;
    private final ClubEmployeeRepository clubEmployeeRepository;
    private final LeagueEmployeeRepository leagueEmployeeRepository;
    private final MatchRepository matchRepository;
    private final RefereeingRepository refereeingRepository;
    private final ParticipatingRepository participatingRepository;
    private final RankingRepository rankingRepository;
    private final TopPlayerRepository topPlayerRepository;
    private final TopAttendanceRepository topAttendanceRepository;

    public CrmService(StadiumRepository stadiumRepository,
                      TeamRepository teamRepository,
                      LeagueSeasonRepository leagueSeasonRepository,
                      ClubEmployeeRepository clubEmployeeRepository,
                      LeagueEmployeeRepository leagueEmployeeRepository,
                      MatchRepository matchRepository,
                      RefereeingRepository refereeingRepository,
                      ParticipatingRepository participatingRepository,
                      RankingRepository rankingRepository,
                      TopPlayerRepository topPlayerRepository,
                      TopAttendanceRepository topAttendanceRepository){

        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.clubEmployeeRepository = clubEmployeeRepository;
        this.leagueEmployeeRepository = leagueEmployeeRepository;
        this.matchRepository = matchRepository;
        this.refereeingRepository = refereeingRepository;
        this.participatingRepository = participatingRepository;
        this.rankingRepository = rankingRepository;
        this.topPlayerRepository = topPlayerRepository;
        this.topAttendanceRepository = topAttendanceRepository;
    }

    public List<Stadium> findAllStadiums(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return stadiumRepository.findAll();
        }
        else{
            return stadiumRepository.search(filterText);
        }
    }

    public List<Stadium> findAllStadiums(){
        return stadiumRepository.findAll();
    }

    public void deleteStadium(Stadium stadium){
        stadiumRepository.delete(stadium);
    }

    public void saveStadium(Stadium stadium){
        if(stadium == null){
            System.err.println("Stadium is null");
        }

        stadiumRepository.save(stadium);
    }

    public List<Team> findAllTeams(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return teamRepository.findAll();
        }
        else{
            return teamRepository.search(filterText);
        }
    }

    public List<Team> findAllTeams(){
        return teamRepository.findAll();
    }

    public long countTeams() {
        return teamRepository.count();
    }

    public void deleteTeam(Team team){
        teamRepository.delete(team);
    }

    public void saveTeam(Team team){
        if(team == null){
            System.err.println("Team is null");
        }

        teamRepository.save(team);
    }

    public List<ClubEmployee> findAllClubEmployees(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return clubEmployeeRepository.findAll();
        }
        else{
            return clubEmployeeRepository.search(filterText);
        }
    }

    public List<ClubEmployee> findAllClubEmployees(){
        return clubEmployeeRepository.findAll();
    }

    public List<ClubEmployee> findAllClubPlayers(String theRole){
        return clubEmployeeRepository.findByRole(theRole);
    }

    public long countClubEmployees() {
        return clubEmployeeRepository.count();
    }

    public void deleteClubEmployee(ClubEmployee clubEmployee){
        clubEmployeeRepository.delete(clubEmployee);
    }

    public void saveClubEmployee(ClubEmployee clubEmployee){
        if(clubEmployee == null){
            System.err.println("ClubEmployee is null");
        }

        clubEmployeeRepository.save(clubEmployee);
    }

    public List<LeagueSeason> findAllLeagueSeasons(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return leagueSeasonRepository.findAll();
        }
        else{
            return leagueSeasonRepository.search(filterText);
        }
    }

    public List<String> findAllLeagueSeasonsNames(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return leagueSeasonRepository.searchAllNames();
        }
        else{
            return leagueSeasonRepository.searchName(filterText);
        }
    }

    public List<LeagueSeason> findAllLeagueSeasons(){
        return leagueSeasonRepository.findAll();
    }

    public void deleteLeagueSeason(LeagueSeason leagueSeason){
        leagueSeasonRepository.delete(leagueSeason);
    }

    public void saveLeagueSeason(LeagueSeason leagueSeason){
        if(leagueSeason == null){
            System.err.println("Stadium is null");
        }

        leagueSeasonRepository.save(leagueSeason);
    }

    public List<LeagueEmployee> findAllLeagueEmployees(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return leagueEmployeeRepository.findAll();
        }
        else{
            return leagueEmployeeRepository.search(filterText);
        }
    }

    public List<LeagueEmployee> findAllLeagueEmployees(){
        return leagueEmployeeRepository.findAll();
    }

    public List<LeagueEmployee> findAllLeagueDelegates(String theRole){
        return leagueEmployeeRepository.findByRole(theRole);
    }

    public List<LeagueEmployee> findAllLeagueReferees(String theRole){
        return leagueEmployeeRepository.findByRole(theRole);
    }

    public long countLeagueEmployees() {
        return leagueEmployeeRepository.count();
    }

    public void deleteLeagueEmployee(LeagueEmployee leagueEmployee){
        leagueEmployeeRepository.delete(leagueEmployee);
    }

    public void saveLeagueEmployee(LeagueEmployee leagueEmployee){
        if(leagueEmployee == null){
            System.err.println("LeagueEmployee is null");
        }

        leagueEmployeeRepository.save(leagueEmployee);
    }

    public List<Match> findAllMatches(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return matchRepository.findAll();
        }
        else{
            return matchRepository.search(filterText);
        }
    }

    public List<Match> findAllMatches(){
        return matchRepository.findAll();
    }

    public void deleteMatch(Match match){
        matchRepository.delete(match);
    }

    public void saveMatch(Match match){
        if(match == null){
            System.err.println("Match is null");
        }

        matchRepository.save(match);
    }

    public List<Refereeing> findAllRefereeing(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return refereeingRepository.findAll();
        }
        else{
            return refereeingRepository.search(filterText);
        }
    }

    public List<Refereeing> findAllRefereeing(){
        return refereeingRepository.findAll();
    }

    public void deleteRefereeing(Refereeing refereeing){
        refereeingRepository.delete(refereeing);
    }

    public void saveRefereeing(Refereeing refereeing){
        if(refereeing == null){
            System.err.println("Refereeing is null");
        }

        refereeingRepository.save(refereeing);
    }

    public List<Participating> findAllParticipating(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return participatingRepository.findAll();
        }
        else{
            return participatingRepository.search(filterText);
        }
    }

    public List<Participating> findAllParticipating(){
        return participatingRepository.findAll();
    }

    public void deleteParticipating(Participating participating){
        participatingRepository.delete(participating);
    }

    public void saveParticipating(Participating participating){
        if(participating == null){
            System.err.println("Participating is null");
        }

        participatingRepository.save(participating);
    }

    public List<Ranking> findAllRanking(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return rankingRepository.findAll();
        }
        else{
            return rankingRepository.search(filterText);
        }
    }

    public List<Ranking> findAllRanking(){
        return rankingRepository.findAll();
    }

    public List<TopPlayer> findAllTopPlayer(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return topPlayerRepository.findAll();
        }
        else{
            return topPlayerRepository.search(filterText);
        }
    }

    public List<TopPlayer> findAllTopPlayer(){
        return topPlayerRepository.findAll();
    }

    public List<TopAttendance> findAllTopAttendance(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return topAttendanceRepository.findAll();
        }
        else{
            return topAttendanceRepository.search(filterText);
        }
    }

    public List<TopAttendance> findAllTopAttendance(){
        return topAttendanceRepository.findAll();
    }

}
