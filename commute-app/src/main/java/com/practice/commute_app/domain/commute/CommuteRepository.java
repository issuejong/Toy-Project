package com.practice.commute_app.domain.commute;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
    Optional<Commute> findByEmployeeIdAndWorkEndTimeIsNull(Long employeeId);
    boolean existsCommuteByEmployeeIdAndWorkEndTimeIsNull(Long employeeId);
    Commute findByEmployeeId(Long employeeId);

    List<Commute> findByEmployeeIdAndWorkStartTimeBetween(Long employeeId, LocalDateTime startMonth, LocalDateTime EndMonth);
}
