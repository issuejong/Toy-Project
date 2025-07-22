package com.practice.commute_app.service.Team;

import com.practice.commute_app.domain.team.Team;
import com.practice.commute_app.domain.team.TeamRepository;
import com.practice.commute_app.dto.team.reponse.TeamResponse;
import com.practice.commute_app.dto.team.request.TeamCreateRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void saveTeam(TeamCreateRequest request) {
        teamRepository.save(new Team(request.getName(), request.getCanDayOff()));
    }

    public List<TeamResponse> getTeam() {
        return teamRepository.findAll().stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
    }
}
