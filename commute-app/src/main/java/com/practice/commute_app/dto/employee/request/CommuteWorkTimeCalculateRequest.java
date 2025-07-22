package com.practice.commute_app.dto.employee.request;

import java.time.LocalDate;

public class CommuteWorkTimeCalculateRequest {

    private Long employeeId;
    private String date;

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
