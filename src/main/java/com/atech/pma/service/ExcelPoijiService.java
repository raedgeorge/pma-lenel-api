package com.atech.pma.service;

import com.atech.pma.entity.mysql.Excel;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */
public interface ExcelPoijiService {

    List<Excel> getListFromExcelFile();
}
