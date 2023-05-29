package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.Car;
import com.atech.pma.entity.mysql.CarModel;
import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.entity.mysql.CardHolderCarInfo;
import com.atech.pma.model.ExcelEmployees;
import com.atech.pma.repository.mysql.CardHolderRepository;
import com.atech.pma.service.CarService;
import com.atech.pma.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final CarService carService;
    private final CardHolderRepository cardHolderRepository;

    @Override
    @Transactional
    public String saveAllToDatabase(List<ExcelEmployees> excelList) {

        int currentEmployeesCount = cardHolderRepository.findAll().size();

        excelList.forEach(emp -> {

            Optional<CardHolder> optionalCardHolder = cardHolderRepository.findCardHolderByBadgeId(emp.getBadgeId());

            if (!optionalCardHolder.isPresent()){

                CardHolder cardHolder = getCardHolder(emp);
                CardHolderCarInfo cardHolderCarInfo = getCardHolderCarInfo(emp);

                cardHolder.setCardHolderCarInfo(cardHolderCarInfo);
                cardHolderRepository.save(cardHolder);
            }

            cardHolderRepository.findAllByEmployeeId(emp.getEmployeeId()).forEach(employee -> {

                System.out.println(employee.getEmployeeId());

                Optional<CardHolder> optCardHolder = cardHolderRepository.findCardHolderByBadgeId(employee.getBadgeId());

                if (optCardHolder.isPresent()){

                    CardHolder cardHolder = optCardHolder.get();
                    cardHolder.setDrivingLicenseExpiryDate(getLocalDate(emp.getDrivingLicenseExpiryDate()));
                    CardHolderCarInfo cardHolderCarInfo = getCardHolderCarInfo(emp);
                    addCarBrandOrCarModelIfNotFoundInDB(cardHolderCarInfo);
                    cardHolder.setCardHolderCarInfo(cardHolderCarInfo);

                    cardHolderRepository.save(cardHolder);
                }
            });

        });

        int updatedEmployeesCount = cardHolderRepository.findAll().size();

        return updatedEmployeesCount - currentEmployeesCount > 0 ? "true" : "false";
    }

    private void addCarBrandOrCarModelIfNotFoundInDB(CardHolderCarInfo cardHolderCarInfo) {

        Optional<Car> optionalCar = carService.findByBrandName(cardHolderCarInfo.getCarBrand());

        if (optionalCar.isPresent()){
            Optional<CarModel> optionalCarModel = carService.findByModelName(cardHolderCarInfo.getCarModel());

            if (!optionalCarModel.isPresent()){
                carService.addModelToBrand(cardHolderCarInfo.getCarModel(), optionalCar.get().getBrandName());
            }
        }
        else {
            carService.addCarBrand(cardHolderCarInfo.getCarBrand());
            carService.addModelToBrand(cardHolderCarInfo.getCarModel(), cardHolderCarInfo.getCarBrand());
        }
    }

    private static CardHolder getCardHolder(ExcelEmployees employee) {

        CardHolder cardHolder = new CardHolder();
        cardHolder.setFirstName(employee.getFirstName());
        cardHolder.setLastName(employee.getLastName());
        cardHolder.setBadgeId(employee.getBadgeId());
        cardHolder.setEmployeeId(employee.getEmployeeId());
        cardHolder.setDrivingLicenseExpiryDate(getLocalDate(employee.getDrivingLicenseExpiryDate()));
        return cardHolder;
    }

    private static CardHolderCarInfo getCardHolderCarInfo(ExcelEmployees employee) {

        CardHolderCarInfo cardHolderCarInfo = new CardHolderCarInfo();
        cardHolderCarInfo.setCarBrand(employee.getCarBrand());
        cardHolderCarInfo.setCarModel(employee.getCarModel());
        cardHolderCarInfo.setColor(employee.getColor());
        cardHolderCarInfo.setProductionYear(employee.getProductionYear());
        cardHolderCarInfo.setFloor(employee.getFloor());
        cardHolderCarInfo.setLaneNumber(employee.getLaneNumber());
        cardHolderCarInfo.setPlateNumber(employee.getPlateNumber());
        cardHolderCarInfo.setInsuranceExpiryDate(getLocalDate(employee.getInsuranceExpiryDate()));
        cardHolderCarInfo.setRegistrationExpiryDate(getLocalDate(employee.getRegistrationExpiryDate()));
        return cardHolderCarInfo;
    }

    private static LocalDate getLocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        return LocalDate.parse(stringDate, formatter);
    }
}
