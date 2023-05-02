package com.atech.pma.entity.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events_history")
public class EventHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String userName;

    @Column(name = "events", nullable = false)
    private String event;

    @CreationTimestamp
    @Column(name = "event_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime eventDate;
}
