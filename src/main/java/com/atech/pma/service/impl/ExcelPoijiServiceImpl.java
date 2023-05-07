package com.atech.pma.service.impl;

import com.atech.pma.model.ExcelEmployees;
import com.atech.pma.service.ExcelPoijiService;
import com.poiji.bind.Poiji;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Service
public class ExcelPoijiServiceImpl implements ExcelPoijiService {

    @Value("${excelFilePath}")
    private String filePath;

    @Override
    public List<ExcelEmployees> getListFromExcelFile() {

        File file = new File(filePath);
        return Poiji.fromExcel(file, ExcelEmployees.class);
    }
}
