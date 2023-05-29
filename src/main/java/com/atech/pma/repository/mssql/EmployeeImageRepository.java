package com.atech.pma.repository.mssql;

import com.atech.pma.entity.mssql.EmployeeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeImageRepository extends JpaRepository<EmployeeImage, Long> {
}
