package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="league_employees")
public class LeagueEmployee {

    @Id
    @Column(name="pesel")
    @Size(min = 11, message = "wielkość musi być równa 11")
    @Size(max = 11, message = "wielkość musi być równa 11")
    @Pattern(regexp="^[0-9]*$", message = "pesel może składać się tylko z cyfr")
    @NotEmpty
    private String pesel;

    @Column(name="first_name")
    @Size(max = 20)
    @NotEmpty
    private String firstName;

    @Column(name="last_name")
    @Size(max = 30)
    @NotEmpty
    private String lastName;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="role")
    @NotEmpty
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leagueEmployee", cascade = CascadeType.REMOVE)
    private List<Match> matches = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pesel", cascade = CascadeType.REMOVE)
    private List<Refereeing> refereeings = new LinkedList<>();

    public LeagueEmployee() {
    }

    public LeagueEmployee(String pesel, String firstName, String lastName, Date dateOfBirth, String role) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Refereeing> getRefereeings() {
        return refereeings;
    }

    public void setRefereeings(List<Refereeing> refereeings) {
        this.refereeings = refereeings;
    }

    @Override
    public String toString() {
        return "LeagueEmployee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}