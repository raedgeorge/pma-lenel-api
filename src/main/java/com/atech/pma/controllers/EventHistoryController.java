package com.atech.pma.controllers;

import com.atech.pma.model.EventHistoryDTO;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventHistoryController {

    private final EventHistoryService eventHistoryService;

    @GetMapping
    public ResponseEntity<List<EventHistoryDTO>> getAllEvents(){

        return ResponseEntity.ok().body(eventHistoryService.listAllEvents());
    }
}
