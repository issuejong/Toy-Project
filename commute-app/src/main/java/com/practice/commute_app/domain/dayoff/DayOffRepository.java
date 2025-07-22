package com.practice.commute_app.domain.dayoff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    List<DayOff> findByEmployeeId(Long employeeId);
    boolean existsDayOffByEmployeeId(Long employeeId);
}
