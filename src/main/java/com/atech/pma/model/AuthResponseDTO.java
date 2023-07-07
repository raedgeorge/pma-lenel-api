package com.atech.pma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {

    private int status;
    private String message;
    private String token;
    private String firstName;
    private String lastName;
    private String role;

}