package com.atech.pma.repository.mssql;

import com.atech.pma.entity.mssql.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
public interface BadgeRepository extends JpaRepository<Badge, Integer> {

    List<Badge> findAllByEmployeeId(int employeeId);

}
