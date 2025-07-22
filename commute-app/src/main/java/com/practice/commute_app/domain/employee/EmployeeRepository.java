package com.practice.commute_app.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByTeamIdAndRole(Long id, Role role);
}
