package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.Alert;
import com.atech.pma.mappers.AlertsMapper;
import com.atech.pma.model.AlertDTO;
import com.atech.pma.repository.mysql.AlertsRepository;
import com.atech.pma.service.AlertsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AlertsServiceImpl implements AlertsService {

    private final AlertsMapper alertsMapper;
    private final AlertsRepository alertsRepository;

    @Override
    public List<AlertDTO> findAllActiveAlerts() {

        List<AlertDTO> alertDTOList = new ArrayList<>();

        alertsRepository.findAll().forEach(alert -> {
            alertDTOList.add(alertsMapper.toDto(alert));
        });

        return alertDTOList;
    }

    @Override
    public void addNewAlert(AlertDTO alertDTO) {

        if (alertDTO != null){
            Alert alert = alertsMapper.toEntity(alertDTO);
            alertsRepository.save(alert);
        }
    }

    @Override
    public AlertDTO findAlertByEmployeeBadgeId(int employeeBadgeId) {

        Optional<Alert> optionalAlert = alertsRepository.findAlertByEmployeeBadgeId(employeeBadgeId);
        return optionalAlert.map(alertsMapper::toDto).orElse(null);
    }

    @Override
    public void deleteAlertByBadgeId(int employeeBadgeId) {

        if (employeeBadgeId == 0L){
            //TODO
        }

        Optional<Alert> optionalAlert = alertsRepository.findAlertByEmployeeBadgeId(employeeBadgeId);

        if (!optionalAlert.isPresent()){
            // TODO
        }

        alertsRepository.deleteByEmployeeBadgeId(employeeBadgeId);
    }
}
