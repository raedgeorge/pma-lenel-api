package com.atech.pma.controllers;

import com.atech.pma.entity.mssql.Employee;
import com.atech.pma.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> loadAllEmployees(){

        System.out.println("loading from onguard...");
        return ResponseEntity.ok().body(employeeService.loadAllEmployeesFromOnguard());
    }

}
