package com.practice.commute_app.controller.team;

import com.practice.commute_app.dto.team.reponse.TeamResponse;
import com.practice.commute_app.dto.team.request.TeamCreateRequest;
import com.practice.commute_app.service.Team.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/team")
    public void saveTeam(@RequestBody TeamCreateRequest request) {
        teamService.saveTeam(request);
    }

    @GetMapping("/team")
    public List<TeamResponse> getTeam() {
        return teamService.getTeam();
    }
}
