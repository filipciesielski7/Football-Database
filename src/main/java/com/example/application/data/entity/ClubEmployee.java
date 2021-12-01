package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name="club_employees")
public class ClubEmployee {

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

    @Column(name="salary")
    @Min(value = 1)
    @Max(value = 1000000)
    private int salary;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="role")
    @NotEmpty
    private String role;

    @Column(name="position")
    @Size(max = 20)
    private String position;

    @Column(name="function")
    @Size(max = 20)
    private String function;

    @ManyToOne
    @Nullable
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

    @Override
    public String toString() {
        return "ClubEmployee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}