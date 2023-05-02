package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.EventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
}
