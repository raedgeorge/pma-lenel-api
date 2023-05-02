package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.Car;
import com.atech.pma.entity.mysql.CarModel;
import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.model.AppUserDTO;
import com.atech.pma.repository.mysql.CarRepository;
import com.atech.pma.service.AppUserService;
import com.atech.pma.service.CarService;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author raed abu Sa'da
 * on 05/04/2023
 */
@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final AppUserService appUserService;
    private final EventHistoryService eventHistoryService;

    @Override
    public String addCarBrand(String brandName) {

        brandName = brandName.trim();

        Optional<Car> optionalCar = carRepository.findByBrandName(brandName.toLowerCase());
        if (optionalCar.isPresent()){

            saveCurrentEventToDatabase(String.format("ERROR. adding new car brand [%s] failed", brandName));

            return "failed";
        }
        else {
            Car car = new Car();

            String modifiedBrandName = convertFirstLetterToUpperCase(brandName);

            car.setBrandName(modifiedBrandName);
            carRepository.save(car);

            saveCurrentEventToDatabase(String.format("new car brand [%s] added to the database", brandName));

            return ("success");
        }

    }


    @Override
    @Transactional
    public String addModelToBrand(String modelName, String brandName) {

        Optional<Car> optionalCar = carRepository.findByBrandName(brandName.toLowerCase());

        if (optionalCar.isPresent()){
            Car car = optionalCar.get();

            CarModel carModel = new CarModel();
            carModel.setModelName(convertFirstLetterToUpperCase(modelName));

            car.getCarModels().add(carModel);
            carRepository.save(car);

            saveCurrentEventToDatabase(String.format("saving new car model [%s] to car brand [%s]", modelName, brandName));

            return "success";
        }
        saveCurrentEventToDatabase(String.format("ERROR. saving new car model [%s] to car brand [%s] failed", modelName, brandName));

        return "failed";
    }

    @Override
    @Transactional
    public List<Car> getAllCars() {

        List<Car> carList = new ArrayList<>();

        carRepository.findAll().forEach(car -> carList.add(car));

        saveCurrentEventToDatabase("get a list of all cars in the database");

        return carList;
    }

    private String convertFirstLetterToUpperCase(String brandName) {

        brandName = brandName.toLowerCase();
        return brandName.substring(0, 1).toUpperCase().concat(brandName.substring(1));
    }

    private void saveCurrentEventToDatabase(String event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedUserId = (String) authentication.getPrincipal();

        AppUserDTO appUserDTO = appUserService.loadUserByBadgeId(loggedUserId);

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName(appUserDTO.getFirstName().concat(" ").concat(appUserDTO.getLastName()))
                .event(event)
                .build());
    }
}
