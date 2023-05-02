package com.atech.pma.repository.mssql;

import com.atech.pma.entity.mssql.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
