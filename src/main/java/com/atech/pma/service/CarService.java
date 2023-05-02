package com.atech.pma.service;

import com.atech.pma.entity.mysql.Car;

import java.util.List;


/**
 * @author raed abu Sa'da
 * on 05/04/2023
 */
public interface CarService {
    String addCarBrand(String brandName);

    String addModelToBrand(String modelName, String brandName);

    List<Car> getAllCars();
}
