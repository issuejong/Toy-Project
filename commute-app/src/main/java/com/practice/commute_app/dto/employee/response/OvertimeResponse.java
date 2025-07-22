package com.practice.commute_app.dto.employee.response;

import lombok.Getter;

@Getter
public class OvertimeResponse {

    private Long employeeId;
    private String name;
    private long overtimeMinutes;

    public OvertimeResponse(Long employeeId, String name, long overtimeMinutes) {
        this.employeeId = employeeId;
        this.name = name;
        this.overtimeMinutes = overtimeMinutes;
    }
}
