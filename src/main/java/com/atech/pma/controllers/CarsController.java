package com.atech.pma.controllers;

import com.atech.pma.entity.mysql.Car;
import com.atech.pma.model.CarWebModel;
import com.atech.pma.model.WebResponseMessage;
import com.atech.pma.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 05/04/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<WebResponseMessage> createNewBrand(@RequestBody CarWebModel carWebModel){

        WebResponseMessage webResponseMessage = new WebResponseMessage();
        webResponseMessage.setMessage(carService.addCarBrand(carWebModel.getBrandName()));

        return ResponseEntity.ok().body(webResponseMessage);
    }

    @PostMapping("/add-model/{brandName}/{modelName}")
    public ResponseEntity<WebResponseMessage> addModelToBrand(@PathVariable String brandName,
                                                  @PathVariable String modelName){

        WebResponseMessage webResponseMessage = new WebResponseMessage();
        webResponseMessage.setMessage(carService.addModelToBrand(modelName, brandName));

       return ResponseEntity.ok().body(webResponseMessage);

    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(){

        return ResponseEntity.ok().body(carService.getAllCars());
    }

}
