package com.atech.pma.service;

import com.atech.pma.entity.mssql.Employee;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */
public interface EmployeeService {

    List<Employee> loadAllEmployeesFromOnguard();
}
