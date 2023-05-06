package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.Excel;
import com.atech.pma.repository.mysql.ExcelRepository;
import com.atech.pma.service.ExcelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Service
public class ExcelServiceImpl implements ExcelService {

    private final ExcelRepository excelRepository;

    public ExcelServiceImpl(ExcelRepository excelRepository) {
        this.excelRepository = excelRepository;
    }

    @Override
    public void saveAllToDatabase(List<Excel> excelList) {

        excelRepository.saveAll(excelList);
    }
}
