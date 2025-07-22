package com.practice.commute_app.domain.commute;

import com.practice.commute_app.domain.employee.Employee;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;

    public Commute(Long id, LocalDateTime workStartTime, LocalDateTime workEndTime) {
        this.id = id;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
    }

    public Commute() {

    }

    public Commute(Employee employee, LocalDateTime workStartTime) {
        this.employee = employee;
        this.workStartTime = workStartTime;
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getWorkStartTime() {
        return workStartTime;
    }

    public LocalDateTime getWorkEndTime() {
        return workEndTime;
    }

    public void updateEndWork() {
        this.workEndTime = LocalDateTime.now();
    }

    public long calDailyWorkTime() {
        return Duration.between(this.workStartTime, this.workEndTime).toMinutes();
    }
}
