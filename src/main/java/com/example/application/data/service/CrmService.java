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

    public CrmService(StadiumRepository stadiumRepository,
                      TeamRepository teamRepository,
                      LeagueSeasonRepository leagueSeasonRepository,
                      ClubEmployeeRepository clubEmployeeRepository,
                      LeagueEmployeeRepository leagueEmployeeRepository){

        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
        this.leagueSeasonRepository = leagueSeasonRepository;
        this.clubEmployeeRepository = clubEmployeeRepository;
        this.leagueEmployeeRepository = leagueEmployeeRepository;
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

}
