package com.atech.pma.controllers;

import com.atech.pma.model.AlertDTO;
import com.atech.pma.service.AlertsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/alerts")
public class AlertsController {

    private final AlertsService alertsService;

    @GetMapping("/list")
    public ResponseEntity<List<AlertDTO>> getAllAlerts(){

        return ResponseEntity.ok().body(alertsService.findAllActiveAlerts());
    }

    @DeleteMapping("/delete")
    public void deleteAlert(@RequestParam("badgeId") int employeeBadgeId){

        alertsService.deleteAlertByBadgeId(employeeBadgeId);
    }
}
