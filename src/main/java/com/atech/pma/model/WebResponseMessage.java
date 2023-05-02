package com.atech.pma.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author raed abu Sa'da
 * on 05/04/2023
 */
@Data
public class WebResponseMessage implements Serializable {

    public static final long serialVersionUID = 42L;

    @JsonProperty("message")
    String message;
}
