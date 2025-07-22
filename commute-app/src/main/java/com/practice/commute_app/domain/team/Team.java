package com.practice.commute_app.domain.team;

import com.practice.commute_app.domain.employee.Employee;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Employee> members = new ArrayList<>();

    private Boolean existManager;

    private int canDayOff;

    public Team(String name, int canDayOff) {
        this.name = name;
        this.canDayOff = canDayOff;
    }

    public Boolean getExistManager() {
        return existManager;
    }

    public Team() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Employee> getMembers() {
        return members;
    }

    public int getCanDayOff() {
        return canDayOff;
    }

    public void addMember(Employee employee) {
        members.add(employee);
    }
}
