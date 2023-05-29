package com.atech.pma.controllers;

import com.atech.pma.entity.mssql.EmployeeImage;
import com.atech.pma.service.EmployeeImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employee/image")
public class EmployeeImageController {

    private final EmployeeImageService employeeImageService;

    @GetMapping
    public ResponseEntity<EmployeeImage> getSingleEmployeeImage(@RequestParam Long empId){

        return ResponseEntity.ok().body(employeeImageService.getEmployeeWithImage(empId).get());
    }
}
