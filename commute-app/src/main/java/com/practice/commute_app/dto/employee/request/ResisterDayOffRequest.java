package com.practice.commute_app.dto.employee.request;

import java.time.LocalDate;

public class ResisterDayOffRequest {

    public Long employeeId;
    public LocalDate useDayOff;

    public Long getEmployeeId() {
        return employeeId;
    }

    public LocalDate getUseDayOff() {
        return useDayOff;
    }
}
