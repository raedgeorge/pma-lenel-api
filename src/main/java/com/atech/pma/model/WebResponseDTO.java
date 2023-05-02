package com.atech.pma.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
@Data
@Builder
public class WebResponseDTO {

    private int status;
    private String message;
}
