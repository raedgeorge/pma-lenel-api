package com.atech.pma.service.impl;

import com.atech.pma.entity.mssql.EmployeeImage;
import com.atech.pma.repository.mssql.EmployeeImageRepository;
import com.atech.pma.service.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeImageServiceImpl implements EmployeeImageService {

    private final EmployeeImageRepository employeeImageRepository;

    @Override
    public Optional<EmployeeImage> getEmployeeWithImage(Long empId) {
        return employeeImageRepository.findById(empId);
    }
}
