package com.atech.pma.mappers;

import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.model.EventHistoryDTO;
import org.mapstruct.Mapper;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */

@Mapper
public interface EventHistoryMapper extends EntityMapper<EventHistoryDTO, EventHistory>{
}
