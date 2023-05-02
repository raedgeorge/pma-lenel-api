package com.atech.pma.service.impl;

import com.atech.pma.entity.mssql.Employee;
import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.model.AppUserDTO;
import com.atech.pma.repository.mssql.EmployeeRepository;
import com.atech.pma.service.AppUserService;
import com.atech.pma.service.EmployeeService;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppUserService appUserService;
    private final EventHistoryService eventHistoryService;

    @Override
    public List<Employee> loadAllEmployeesFromOnguard() {

        saveCurrentEventToDatabase("get a list of all employees from OnGuard database");

        return employeeRepository.findAll();
    }

    private void saveCurrentEventToDatabase(String event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedUserId = (String) authentication.getPrincipal();

        AppUserDTO appUserDTO = appUserService.loadUserByBadgeId(loggedUserId);

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName(appUserDTO.getFirstName().concat(" ").concat(appUserDTO.getLastName()))
                .event(event)
                .build());
    }
}
