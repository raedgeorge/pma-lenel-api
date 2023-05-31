package com.atech.pma.controllers;

import com.atech.pma.reports.ReportsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

/**
 * @author raed abu Sa'da
 * on 07/04/2023
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    /**
     *
     * @param fileFormat pdf or excel
     */
    @GetMapping
    public void allCardHoldersReport(@RequestParam String fileFormat,
                                     HttpServletResponse response){

        reportsService.generateAllCardHoldersReport(fileFormat, response);
    }


    @GetMapping("/{badgeId}")
    public void getSingleCardHolderReport(@PathVariable int badgeId,
                                                                @RequestParam String fileFormat,
                                                                HttpServletResponse response){

        reportsService.generateSingleCardHolderReport(badgeId, fileFormat, response);
    }

    /**
     * filter data
     * @param startDate start date
     * @param endDate end date
     */
    @GetMapping("/filtered")
    public void employeesReportByDrivingLicenseFilter(@RequestParam String startDate,
                                                                                  @RequestParam String endDate,
                                                                                  @RequestParam String fileFormat,
                                                                                  HttpServletResponse response){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        reportsService.generateReportByDrivingLicense(start, end, fileFormat, response);
    }

    @GetMapping("/filter")
    public void employeesReportByInsuranceDate(@RequestParam String startDate,
                                               @RequestParam String endDate,
                                               @RequestParam String fileFormat,
                                               HttpServletResponse response){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        reportsService.generateReportByCarInsurance(start, end, fileFormat, response);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<?> getPdfFile(@PathVariable int id) throws MalformedURLException {

        File file = reportsService.getFile(id);

        Resource resource = new UrlResource(file.getPath());

        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", file.getName());
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name= " + resource.getFilename());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("pdf"))
                .headers(headers).body(resource);
    }
}
