package com.atech.pma.service;

import com.atech.pma.model.AlertDTO;

import java.util.List;

public interface AlertsService {

    List<AlertDTO> findAllActiveAlerts();

    void addNewAlert(AlertDTO alert);

    AlertDTO findAlertByEmployeeBadgeId(int employeeBadgeId);
    void deleteAlertByBadgeId(int employeeBadgeId);
}
