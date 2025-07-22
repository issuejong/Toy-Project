package com.practice.commute_app.service.Employee;

import com.practice.commute_app.domain.commute.Commute;
import com.practice.commute_app.domain.commute.CommuteRepository;
import com.practice.commute_app.domain.dayoff.DayOff;
import com.practice.commute_app.domain.dayoff.DayOffRepository;
import com.practice.commute_app.domain.employee.Employee;
import com.practice.commute_app.domain.employee.EmployeeRepository;
import com.practice.commute_app.domain.employee.Role;
import com.practice.commute_app.domain.team.Team;
import com.practice.commute_app.domain.team.TeamRepository;
import com.practice.commute_app.dto.employee.request.*;
import com.practice.commute_app.dto.employee.response.OvertimeResponse;
import com.practice.commute_app.dto.employee.response.commute.CommuteResponse;
import com.practice.commute_app.dto.employee.response.EmployeeResponse;
import com.practice.commute_app.dto.employee.response.commute.DailyWorkResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final CommuteRepository commuteRepository;
    private final DayOffRepository dayOffRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TeamRepository teamRepository, CommuteRepository commuteRepository, DayOffRepository dayOffRepository) {
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.commuteRepository = commuteRepository;
        this.dayOffRepository = dayOffRepository;
    }

    @Transactional
    public void saveEmployee(EmployeeCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                        .orElseThrow(IllegalArgumentException::new);
        Employee employee = new Employee(request.getName(), team, request.getRole(), request.getBirthday(), request.getWorkStartDate());
        if(request.getRole() == Role.MANAGER) {
            if (employeeRepository.existsByTeamIdAndRole(team.getId(), Role.MANAGER)) throw new IllegalArgumentException("매니저가 이미 존재합니다.");
        }
        if(request.getWorkStartDate().getYear() == LocalDate.now().getYear()) employee.dayOffCountIsEleven();
        else employee.dayOffCountIsfifteen();
        employeeRepository.save(employee);
        team.addMember(employee);
    }

    @Transactional
    public List<EmployeeResponse> getEmployee() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void startWork(EmployeeStartWorkRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new);

        if(commuteRepository.existsCommuteByEmployeeIdAndWorkEndTimeIsNull(request.getEmployeeId()))
            throw new IllegalArgumentException("출근을 이미 했습니다.");

        Commute commute = new Commute(employee, LocalDateTime.now());
        commuteRepository.save(commute);
    }

    @Transactional
    public void endWork(EmployeeEndWorkRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new);

        Commute commute = commuteRepository.findByEmployeeIdAndWorkEndTimeIsNull(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new); // 출근을 안 했거나 퇴근을 이미 했으면 예외 처리

        commute.updateEndWork();
    }

    @Transactional
    public CommuteResponse getWorkMinutes(CommuteWorkTimeCalculateRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new);

        List<Commute> commutes = commuteRepository.findByEmployeeIdAndWorkStartTimeBetween(
                request.getEmployeeId(),
                YearMonth.parse(request.getDate()).atDay(1).atStartOfDay(),
                YearMonth.parse(request.getDate()).atEndOfMonth().atTime(LocalTime.MAX)
        );

        Map<LocalDate, Integer> dailyWorkMinutes = new HashMap<>();

        for (Commute commute : commutes) {
            if (commute.getWorkStartTime() != null && commute.getWorkEndTime() != null) {
                LocalDate date = commute.getWorkStartTime().toLocalDate();
                int minutes = (int) ChronoUnit.MINUTES.between(commute.getWorkStartTime(), commute.getWorkEndTime());
                dailyWorkMinutes.merge(date, minutes, Integer::sum);
            }
        }

        List<DailyWorkResponse> detail = dailyWorkMinutes.entrySet().stream()
                .map(entry -> new DailyWorkResponse(entry.getKey().toString(), entry.getValue(), employee.getDayOffList().contains(entry.getValue())))
                .collect(Collectors.toList());

        int sum = detail.stream().mapToInt(DailyWorkResponse::getWorkingMinutes).sum();

        return new CommuteResponse(detail, sum);
    }

    @Transactional
    public void resisterDayOff(ResisterDayOffRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new);
        Team team = employee.getTeam();

        if(ChronoUnit.DAYS.between(request.getUseDayOff(), LocalDate.now()) < team.getCanDayOff())
            throw new IllegalArgumentException();

        employee.minusDayOff();
        employee.getDayOffList().add(new DayOff(employee, request.getUseDayOff()));
    }

    @Transactional
    public List<OvertimeResponse> getOvertime(YearMonth date) {

        List<Employee> employees = employeeRepository.findAll();
        List<OvertimeResponse> result = new ArrayList<>();
        long time = 9600;

        // 한 달 근무 시간 - 한 달 기준 근무 시간
        for(Employee employee : employees) {
            Commute commute = commuteRepository.findByEmployeeId(employee.getId());

            if (commute != null) {
                result.add(new OvertimeResponse(employee.getId(), employee.getName(), (commute.calDailyWorkTime() > time) ? commute.calDailyWorkTime()-time : 0));
            } else {
                result.add(new OvertimeResponse(employee.getId(), employee.getName(), 0)); // 또는 생략 가능
            }
        }

        return result;
    }
}
