package com.practice.commute_app.controller.employee;

import com.practice.commute_app.domain.commute.CommuteRepository;
import com.practice.commute_app.domain.employee.EmployeeRepository;
import com.practice.commute_app.dto.employee.request.*;
import com.practice.commute_app.dto.employee.response.OvertimeResponse;
import com.practice.commute_app.dto.employee.response.commute.CommuteResponse;
import com.practice.commute_app.dto.employee.response.EmployeeResponse;
import com.practice.commute_app.service.Employee.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, CommuteRepository commuteRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.commuteRepository = commuteRepository;
    }

    @PostMapping("/employee")
    public void saveEmployee(@RequestBody EmployeeCreateRequest request) {
        employeeService.saveEmployee(request);
    }

    @GetMapping("/employee")
    public List<EmployeeResponse> getEmployee() {
        return employeeService.getEmployee();
    }

    @PostMapping("/employee/comuute/start")
    public void startWork(@RequestBody EmployeeStartWorkRequest request) {
        employeeService.startWork(request);
    }

    @PostMapping("/employee/commute/end")
    public void endWork(@RequestBody EmployeeEndWorkRequest request) {
        employeeService.endWork(request);
    }

    @GetMapping("/employee/commute/time")
    public CommuteResponse getWorkMinutes(@ModelAttribute CommuteWorkTimeCalculateRequest request) {
       return employeeService.getWorkMinutes(request);
    }

    @PostMapping("/employee/resister/dayoff")
    public void resisterDayOff(@RequestBody ResisterDayOffRequest request) {
        employeeService.resisterDayOff(request);
    }

    @GetMapping("/employee/overtime")
    public List<OvertimeResponse> getOvertime(@RequestParam YearMonth date) {
        return employeeService.getOvertime(date);
    }
}
