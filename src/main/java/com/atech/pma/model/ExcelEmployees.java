package com.atech.pma.model;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Data
public class ExcelEmployees {

    @ExcelCellName("First_Name")
    private String firstName;

    @ExcelCellName("Last_Name")
    private String lastName;

    @ExcelCellName("Badge_Id")
    private int badgeId;

    @ExcelCellName("Employee_Id")
    private Double employeeId;

    @ExcelCellName("Driving_License_Expiry_Date")
    private String drivingLicenseExpiryDate;

    @ExcelCellName("Color")
    private String color;

    @ExcelCellName("Floor")
    private String floor;

    @ExcelCellName("Lane_Number")
    private int laneNumber;

    @ExcelCellName("Car_Brand")
    private String carBrand;

    @ExcelCellName("Car_Model")
    private String carModel;

    @ExcelCellName("Plate_Number")
    private String plateNumber;

    @ExcelCellName("Production_Year")
    private int productionYear;

    @ExcelCellName("Insurance_Expiry_Date")
    private String insuranceExpiryDate;

    @ExcelCellName("Registration_Expiry_Date")
    private String registrationExpiryDate;
}
