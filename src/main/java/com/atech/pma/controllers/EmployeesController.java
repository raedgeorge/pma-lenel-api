package com.atech.pma.controllers;

import com.atech.pma.entity.mssql.Badge;
import com.atech.pma.entity.mssql.Employee;
import com.atech.pma.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok().body(employeeService.loadAllEmployeesFromOnguard());
    }

    @GetMapping("/badge")
    public ResponseEntity<List<Badge>> getBadgeByEmployeeId(@RequestParam int id){

        return ResponseEntity.ok().body(employeeService.getBadgesByEmployeeId(id));
    }

    @GetMapping("/badges/list")
    public ResponseEntity<List<Badge>> getAllBadges(){

        return ResponseEntity.ok().body(employeeService.getAllBadges());
    }

}
