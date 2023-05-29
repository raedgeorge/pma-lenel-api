package com.atech.pma.repository.mssql;

import com.atech.pma.entity.mssql.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */
public interface BadgeRepository extends JpaRepository<Badge, Integer> {

    List<Badge> findAllByEmployeeId(int employeeId);

    @Query("select b from Badge b where b.employeeId in :id")
    List<Badge> getBadgesForEmployee(@Param("id") int id);

}
