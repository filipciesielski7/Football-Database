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

    public CrmService(StadiumRepository stadiumRepository,
                      TeamRepository teamRepository,
                      LeagueSeasonRepository leagueSeasonRepository){

        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
        this.leagueSeasonRepository = leagueSeasonRepository;
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
}
