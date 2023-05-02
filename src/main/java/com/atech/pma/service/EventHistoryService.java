package com.atech.pma.service;

import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.model.EventHistoryDTO;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
public interface EventHistoryService {

    List<EventHistoryDTO> listAllEvents();

    void saveNewEvent(EventHistory eventHistory);
}
