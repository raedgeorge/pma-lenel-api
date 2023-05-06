package com.atech.pma;

import com.atech.pma.entity.mysql.Excel;
import com.atech.pma.service.ExcelPoijiService;
import com.atech.pma.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ExcelService excelService;
    private final ExcelPoijiService excelPoijiService;

    @Override
    public void run(String... args) throws Exception {

        List<Excel> listFromExcelFile = excelPoijiService.getListFromExcelFile();

        excelService.saveAllToDatabase(listFromExcelFile);
        System.out.println("data loaded from excel and saved to the database...");
    }
}
