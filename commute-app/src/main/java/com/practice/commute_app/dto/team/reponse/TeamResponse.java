package com.practice.commute_app.dto.team.reponse;

import com.practice.commute_app.domain.employee.Employee;
import com.practice.commute_app.domain.employee.Role;
import com.practice.commute_app.domain.team.Team;

public class TeamResponse {

    private String name;
    private String managerName;
    private int memberCount;

    public TeamResponse(Team team) {
        this.name = team.getName();
        this.managerName = extractManagerName(team);
        this.memberCount = team.getMembers() != null ? team.getMembers().size() : 0;
    }

    private String extractManagerName(Team team) {
        if (team.getMembers() == null) return null;

        return team.getMembers().stream()
                .filter(e -> e.getRole() == Role.MANAGER)
                .map(Employee::getName)
                .findFirst()
                .orElse(null);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    public int getMemberCount() {
        return memberCount;
    }
}
