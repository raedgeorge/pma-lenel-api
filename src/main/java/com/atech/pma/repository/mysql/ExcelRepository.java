package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Repository
public interface ExcelRepository extends JpaRepository<Excel, Integer> {
}
