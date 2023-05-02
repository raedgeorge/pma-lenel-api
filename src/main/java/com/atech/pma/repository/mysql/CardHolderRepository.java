package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.CardHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */
public interface CardHolderRepository extends JpaRepository<CardHolder, Long> {

    Optional<CardHolder> findCardHolderByBadgeId(int badgeId);

    Optional<List<CardHolder>> findAllByDrivingLicenseExpiryDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM CardHolder e " +
            "JOIN CardHolderCarInfo c " +
            "ON e.cardHolderCarInfo.id = c.id " +
            "WHERE c.insuranceExpiryDate >= ?1 AND c.insuranceExpiryDate <= ?2 ")
    Optional<List<CardHolder>> findCardHoldersFilteredByInsuranceDate(LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM CardHolder e " +
            "JOIN CardHolderCarInfo c " +
            "ON e.cardHolderCarInfo.id = c.id " +
            "WHERE c.insuranceExpiryDate = ?1 ")
    Optional<List<CardHolder>> findCardHoldersByInsuranceDateExpiringToday(LocalDate date);

}
