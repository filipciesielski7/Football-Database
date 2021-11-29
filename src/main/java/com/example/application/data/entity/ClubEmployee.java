package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="club_employees")
public class ClubEmployee {

    @Id
    @Column(name="pesel")
    @NotEmpty
    private String pesel;

    @Column(name="first_name")
    @NotEmpty
    private String firstName;

    @Column(name="last_name")
    @NotEmpty
    private String lastName;

    @Column(name="salary")
    @Min(value = 1)
    @Max(value = 1000000)
    private int salary;


    @Column(name="date_of_birth")
    @NotEmpty
    private Date dateOfBirth;

    @Column(name="role")
    @NotEmpty
    private String role;

    @Column(name="position")
    private String position;

    @Column(name="function")
    private String function;

    @ManyToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name="team_name")
    private Team team;

    public ClubEmployee() {
    }


    public ClubEmployee(String pesel, String firstName, String lastName, int salary, Date dateOfBirth, String role, String position, String function) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.position = position;
        this.function = function;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }



}
