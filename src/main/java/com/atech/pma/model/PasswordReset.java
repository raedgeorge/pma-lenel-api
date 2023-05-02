package com.atech.pma.model;

import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
@Data
public class PasswordReset {

    private String badgeId;
    private String oldPassword;
    private String newPassword;
}
