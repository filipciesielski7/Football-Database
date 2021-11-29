package com.example.application.data.service;

import com.example.application.data.entity.ClubEmployee;
import com.example.application.data.entity.Stadium;
import com.example.application.data.entity.Team;
import com.example.application.data.repository.ClubEmployeeRepository;
import com.example.application.data.repository.StadiumRepository;
import com.example.application.data.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final StadiumRepository stadiumRepository;
    private final TeamRepository teamRepository;
    private final ClubEmployeeRepository clubEmployeeRepository;

    public CrmService(StadiumRepository stadiumRepository,
                      TeamRepository teamRepository,
                      ClubEmployeeRepository clubEmployeeRepository){

        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
        this.clubEmployeeRepository = clubEmployeeRepository;
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

    public void deleteStadium(Stadium stadium) {

        /*Team theTeam = null;
        theTeam = teamRepository.findByStadium(stadium);
        Stadium stadium1 = stadium;
        System.out.println(theTeam);
        if (theTeam != null) {
            System.out.println(theTeam);
            teamRepository.setTeamByName(theTeam.getName());
        }*/
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




}