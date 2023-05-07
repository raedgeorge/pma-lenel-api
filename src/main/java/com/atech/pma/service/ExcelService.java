package com.atech.pma.service;

import com.atech.pma.model.ExcelEmployees;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */
public interface ExcelService {

    String saveAllToDatabase(List<ExcelEmployees> excelList);
}
