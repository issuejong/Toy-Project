package com.practice.commute_app.dto.employee.response.commute;

import java.util.List;

public class CommuteResponse {

    private List<DailyWorkResponse> detail;
    private int sum;

    public CommuteResponse(List<DailyWorkResponse> detail, int sum) {
        this.detail = detail;
        this.sum = sum;
    }

    public List<DailyWorkResponse> getDetail() {
        return detail;
    }

    public int getSum() {
        return sum;
    }

}
