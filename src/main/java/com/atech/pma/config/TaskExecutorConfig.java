package com.atech.pma.config;

import com.atech.pma.service.CardHoldersService;
import com.atech.pma.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    @Scheduled(cron = "00 15 22 ? * FRI")
//    @Scheduled(fixedRate = 50000L, initialDelay = 25000L)
    public void insuranceExpiryCheckWeeklySchedule(){

        cardHoldersService.getCardHoldersLicenseExpireInOneWeek().forEach(cardHolder -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            messageService.sendMessage(cardHolder.getFirstName().toLowerCase().concat(" - ").concat(String.valueOf(cardHolder.getBadgeId())));

        });

        cardHoldersService.getCardHoldersByExpiringDrivingLicense().forEach(cardHolder -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            messageService.sendMessage(cardHolder.getFirstName().toLowerCase().concat(" - ").concat(String.valueOf(cardHolder.getBadgeId())));
        });

        log.info("task executed");
    }
}
