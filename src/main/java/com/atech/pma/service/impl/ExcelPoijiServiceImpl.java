package com.atech.pma.service.impl;

import com.atech.pma.model.ExcelEmployees;
import com.atech.pma.service.ExcelPoijiService;
import com.poiji.bind.Poiji;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Service
public class ExcelPoijiServiceImpl implements ExcelPoijiService {

    private static final String PATH = "D:/";

    @Override
    public List<ExcelEmployees> getListFromExcelFile(String fileName) {

        File file = new File(PATH + fileName);
        return Poiji.fromExcel(file, ExcelEmployees.class);
    }
}
