package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.entity.mysql.CardHolderCarInfo;
import com.atech.pma.model.ExcelEmployees;
import com.atech.pma.repository.mysql.CardHolderRepository;
import com.atech.pma.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final CardHolderRepository cardHolderRepository;

    @Override
    @Transactional
    public String saveAllToDatabase(List<ExcelEmployees> excelList) {

        int currentEmployeesCount = cardHolderRepository.findAll().size();
        System.out.println("current: " + currentEmployeesCount);

        excelList.forEach(employee -> {

            CardHolder cardHolder = getCardHolder(employee);
            CardHolderCarInfo cardHolderCarInfo = getCardHolderCarInfo(employee);

            cardHolder.setCardHolderCarInfo(cardHolderCarInfo);
            if (cardHolderRepository.findCardHolderByBadgeId(cardHolder.getBadgeId()).isEmpty()){
                cardHolderRepository.save(cardHolder);
            }
        });

        int updatedEmployeesCount = cardHolderRepository.findAll().size();
        System.out.println("updated: " + updatedEmployeesCount);

        return updatedEmployeesCount - currentEmployeesCount > 0 ? "true" : "false";
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
