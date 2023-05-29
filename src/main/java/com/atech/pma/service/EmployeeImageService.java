package com.atech.pma.service;

import com.atech.pma.entity.mssql.EmployeeImage;

import java.util.Optional;

public interface EmployeeImageService {

    Optional<EmployeeImage> getEmployeeWithImage(Long empId);
}
