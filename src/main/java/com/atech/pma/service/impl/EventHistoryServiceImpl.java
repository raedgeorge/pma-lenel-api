package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.mappers.EventHistoryMapper;
import com.atech.pma.model.EventHistoryDTO;
import com.atech.pma.repository.mysql.EventHistoryRepository;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */

@Service
@AllArgsConstructor
public class EventHistoryServiceImpl implements EventHistoryService {

    private final EventHistoryMapper eventHistoryMapper;
    private final EventHistoryRepository eventHistoryRepository;

    @Override
    public List<EventHistoryDTO> listAllEvents() {

        List<EventHistoryDTO> eventHistoryDTOList = new ArrayList<>();

        eventHistoryRepository.findTop500Events().forEach(event -> {
            eventHistoryDTOList.add(eventHistoryMapper.toDto(event));
        });

        return eventHistoryDTOList;
    }

    @Override
    public void saveNewEvent(EventHistory eventHistory) {
        eventHistoryRepository.save(eventHistory);
    }
}
