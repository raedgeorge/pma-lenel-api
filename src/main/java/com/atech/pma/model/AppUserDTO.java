package com.atech.pma.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Data
public class AppUserDTO {

    private String firstName;
    private String lastName;
    private String badgeId;
    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
