package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.EventHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {

    @Query("SELECT e FROM EventHistory e ORDER BY e.eventDate DESC LIMIT 1000")
    List<EventHistory> findTop500Events();
}
