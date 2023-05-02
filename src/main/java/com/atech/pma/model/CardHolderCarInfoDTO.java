package com.atech.pma.model;

import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 12/04/2023
 */

@Data
public class CardHolderCarInfoDTO {

    private Long id;
    private String carBrand;
    private String carModel;
    private String color;
    private String floor;
    private int laneNumber;
    private int productionYear;
    private String plateNumber;
    private String insuranceExpiryDate;
    private String registrationExpiryDate;
}
