package com.atech.pma.service;

import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.model.CardHolderDTO;

import java.util.List;


/**
 * @author raed abu Sa'da
 * on 01/04/2023
 */
public interface CardHoldersService {

    List<CardHolder> getCardHoldersList();

    List<CardHolder> getCardHoldersListForSchedule();

    CardHolder findCardHolderById(Long id);

    CardHolder updateCardHolder(CardHolder cardHolder);

    void deleteCardHolder(long id);

    List<CardHolderDTO> getCardHoldersLicenseExpireInOneWeek();

    List<CardHolderDTO> getCardHoldersExpiringToday();

    void populateCardHoldersFromOnguardDB();
}
