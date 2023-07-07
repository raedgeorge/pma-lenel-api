package com.atech.pma.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @Email(message = "Invalid email address")
    @NotBlank(message = "Please enter your email address")
    private String email;

    @NotBlank(message = "Please enter your password")
    @Size(min = 6, max = 10, message = "Password length is between 6 and 10 characters")
    private String password;
}
