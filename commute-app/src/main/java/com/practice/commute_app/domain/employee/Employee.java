package com.practice.commute_app.domain.employee;

import com.practice.commute_app.domain.dayoff.DayOff;
import com.practice.commute_app.domain.team.Team;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDate birthday;

    private LocalDate workStartDate;

    private int dayOffCount;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<DayOff> dayOffList = new ArrayList<>();

    @ManyToOne
    private Team team;

    //constructor
    public Employee(String name, Team team, Role role, LocalDate birthday, LocalDate workStartDate) {
        this.name = name;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
        this.team = team;
    }

    public Employee() {
    }

    //getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate getWorkStartDate() {
        return workStartDate;
    }

    public int getDayOffCount() {
        return dayOffCount;
    }

    public Team getTeam() {
        return team;
    }

    public List<DayOff> getDayOffList() {
        return dayOffList;
    }

    public void minusDayOff() {
        this.dayOffCount--;
    }

    public void dayOffCountIsEleven() {
        this.dayOffCount = 11;
    }

    public void dayOffCountIsfifteen() {
        this.dayOffCount = 15;
    }

}
