package com.example.application.data.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository,
                      TeamRepository teamRepository,
                      StadiumRepository stadiumRepository){

        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
        this.teamRepository = teamRepository;
        this.stadiumRepository = stadiumRepository;
    }

    public List<Contact> findAllContacts(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return contactRepository.findAll();
        }
        else{
            return contactRepository.search(filterText);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact){
        if(contact == null){
            System.err.println("Contact is null");
        }

        contactRepository.save(contact);
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

    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public List<Stadium> findAllStadiums(){
        return stadiumRepository.findAll();
    }
}
