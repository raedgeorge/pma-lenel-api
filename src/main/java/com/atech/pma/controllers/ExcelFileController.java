package com.atech.pma.controllers;

import com.atech.pma.model.ExcelEmployees;
import com.atech.pma.service.ExcelPoijiService;
import com.atech.pma.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/excel")
public class ExcelFileController {

    private final ExcelService excelService;
    private final ExcelPoijiService excelPoijiService;

    @PutMapping("/upload")
    public ResponseEntity<?> readEmployeesFromExcelFile(){

        List<ExcelEmployees> listFromExcelFile = excelPoijiService.getListFromExcelFile();
        excelService.saveAllToDatabase(listFromExcelFile);

        return ResponseEntity.ok().body(listFromExcelFile);
    }
}
