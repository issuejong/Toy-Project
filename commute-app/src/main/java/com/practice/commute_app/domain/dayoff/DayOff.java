package com.practice.commute_app.domain.dayoff;

import com.practice.commute_app.domain.employee.Employee;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public DayOff(Employee employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }

    public DayOff() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
}
