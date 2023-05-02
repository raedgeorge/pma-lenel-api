package com.atech.pma.model;

import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 12/04/2023
 */
@Data
public class CardHolderDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private int employeeId;
    private int badgeId;
    private String drivingLicenseExpiryDate;
    private CardHolderCarInfoDTO cardHolderCarInfo;
}
