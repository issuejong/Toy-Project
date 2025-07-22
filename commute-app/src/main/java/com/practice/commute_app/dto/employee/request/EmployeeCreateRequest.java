package com.practice.commute_app.dto.employee.request;

import com.practice.commute_app.domain.employee.Role;
import com.practice.commute_app.domain.team.Team;

import java.time.LocalDate;
import java.util.Date;

public class EmployeeCreateRequest {
    private String name;
    private Long teamId;
    private Role role;
    private LocalDate birthday;
    private LocalDate workStartDate;

    public String getName() {
        return name;
    }

    public Long getTeamId() {
        return teamId;
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

}
