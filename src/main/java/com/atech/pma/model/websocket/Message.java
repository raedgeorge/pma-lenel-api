package com.atech.pma.model.websocket;

import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */
@Data
public class Message {

    private String text;
    private String to;
}
