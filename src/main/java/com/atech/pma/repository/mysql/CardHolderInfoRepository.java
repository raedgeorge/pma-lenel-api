package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.CardHolderCarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */
public interface CardHolderInfoRepository extends JpaRepository<CardHolderCarInfo, Long> {

}
