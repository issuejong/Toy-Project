package com.practice.commute_app.dto.employee.response.commute;

public class DailyWorkResponse {

    private String date;
    private int workingMinutes;
    private boolean usingDayOff;

    public DailyWorkResponse(String date, int workingMinutes, boolean usingDayOff) {
        this.date = date;
        this.workingMinutes = workingMinutes;
        this.usingDayOff = usingDayOff;
    }

    public String getDate() {
        return date;
    }

    public int getWorkingMinutes() {
        return workingMinutes;
    }

    public boolean isUsingDayOff() {
        return usingDayOff;
    }
}
