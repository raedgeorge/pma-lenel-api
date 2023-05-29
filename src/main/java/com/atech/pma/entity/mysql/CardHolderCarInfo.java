package com.atech.pma.entity.mysql;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */
@Data
@Entity
@Table(name = "card_holder_car_info")
public class CardHolderCarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_brand")
    private String carBrand;

    @Column(name = "car_model")
    private String carModel;

    private String color;

    private String floor;

    @Column(name = "lane_number")
    private int laneNumber;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "plate_number", unique = false)
    private String plateNumber;

    @Column(name = "insurance_expiry_date")
    private LocalDate insuranceExpiryDate;

    @Column(name = "registration_expiry_date")
    private LocalDate registrationExpiryDate;
}
