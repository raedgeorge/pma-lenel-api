package com.atech.pma.reports;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.time.LocalDate;

/**
 * @author raed abu Sa'da
 * on 07/04/2023
 */
public interface ReportsService {

    void generateAllCardHoldersReport(String fileFormat, HttpServletResponse response);

    void generateSingleCardHolderReport(int badgeId, String fileFormat, HttpServletResponse response);
    void generateReportByDrivingLicense(LocalDate startDate, LocalDate endDate, String fileFormat, HttpServletResponse response);

    void generateReportByCarInsurance(LocalDate startDate, LocalDate endDate, String fileFormat, HttpServletResponse response);

    File getFile(long cardHolderId);

}
