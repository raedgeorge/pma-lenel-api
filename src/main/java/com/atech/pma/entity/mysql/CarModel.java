package com.atech.pma.entity.mysql;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */
@Data
@Entity
@Table(name = "car_model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_id")
    private Long carId;

    @Column(name = "model_name")
    private String modelName;
}
