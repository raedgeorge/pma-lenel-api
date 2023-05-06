package com.atech.pma.config;

import com.atech.pma.service.CardHoldersService;
import com.atech.pma.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

/**
 * @author raed abu Sa'da
 * on 14/04/2023
 */

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class TaskExecutorConfig {

    private final CardHoldersService cardHoldersService;
    private final MessageService messageService;

    @Scheduled(cron = "00 00 08 * * ?")
    public void insuranceExpiryCheckDailySchedule(){

        cardHoldersService.getCardHoldersExpiringToday().forEach(cardHolder -> {

            messageService.sendMessage(cardHolder.getFirstName().concat(" ").concat(cardHolder.getLastName()));
        });
        log.info("task executed");
    }

    @Scheduled(cron = "00 41 11 ? * WED")
    public void insuranceExpiryCheckWeeklySchedule(){

        cardHoldersService.getCardHoldersLicenseExpireInOneWeek().forEach(cardHolder -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            messageService.sendMessage(cardHolder.getFirstName().concat(" ").concat(cardHolder.getLastName()));

        });
        log.info("task executed");
    }
}
