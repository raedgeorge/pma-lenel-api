package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertsRepository extends JpaRepository<Alert, Long> {

    void deleteByEmployeeBadgeId(int employeeBadgeId);

    Optional<Alert> findAlertByEmployeeBadgeId(int employeeBadgeId);
}
