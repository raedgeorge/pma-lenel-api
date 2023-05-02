package com.atech.pma.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String employeeName){
        messagingTemplate.convertAndSend("/topic/notification" , employeeName);
    }

}
