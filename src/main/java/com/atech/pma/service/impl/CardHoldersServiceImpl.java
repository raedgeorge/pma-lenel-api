package com.atech.pma.service.impl;

import com.atech.pma.entity.mssql.Employee;
import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.entity.mysql.CardHolderCarInfo;
import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.mappers.CardHolderMapper;
import com.atech.pma.model.AppUserDTO;
import com.atech.pma.model.CardHolderDTO;
import com.atech.pma.repository.mssql.BadgeRepository;
import com.atech.pma.repository.mssql.EmployeeRepository;
import com.atech.pma.repository.mysql.CardHolderRepository;
import com.atech.pma.service.AppUserService;
import com.atech.pma.service.CardHoldersService;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 01/04/2023
 */

@Slf4j
@Service
@AllArgsConstructor
public class CardHoldersServiceImpl implements CardHoldersService {

    private final AppUserService appUserService;
    private final CardHolderMapper cardHolderMapper;
    private final EventHistoryService eventHistoryService;
    private final CardHolderRepository cardHolderRepository;
    private final EmployeeRepository employeeRepository;
    private final BadgeRepository badgeRepository;


    @Override
    @Transactional
    public void populateCardHoldersFromOnguardDB() {

        badgeRepository.findAll().forEach(badge -> {

            Optional<CardHolder> optionalCardHolder = cardHolderRepository.findCardHolderByBadgeId(badge.getBadgeId());

                if (!optionalCardHolder.isPresent()) {

                    Optional<Employee> optionalEmployee = employeeRepository.findById(badge.getEmployeeId());

                    if (optionalEmployee.isPresent()) {

                        Employee employeeInDB = optionalEmployee.get();
                        CardHolder cardHolder = new CardHolder();

                        cardHolder.setEmployeeId(employeeInDB.getEmployeeId());
                        cardHolder.setBadgeId(badge.getBadgeId());
                        cardHolder.setFirstName(employeeInDB.getFirstName());
                        cardHolder.setLastName(employeeInDB.getLastName());
                        cardHolder.setDrivingLicenseExpiryDate(LocalDate.now());

                        CardHolderCarInfo cardHolderCarInfo = new CardHolderCarInfo();
                        cardHolderCarInfo.setPlateNumber(RandomStringUtils.randomAlphanumeric(6));
                        cardHolder.setCardHolderCarInfo(cardHolderCarInfo);

                        cardHolderRepository.save(cardHolder);
                    }
                }
        });
    }

    @Override
    @Transactional
    public List<CardHolder> getCardHoldersList() {

        saveCurrentEventToDatabase("Get a list all employees in the system.");

        return cardHolderRepository.findAll();
    }

    @Override
    public List<CardHolder> getCardHoldersListForSchedule() {

        return cardHolderRepository.findAll();
    }

    @Override
    public List<CardHolderDTO> getCardHoldersLicenseExpireInOneWeek() {

        List<CardHolderDTO> cardHolderDTOList = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        Optional<List<CardHolder>> optionalCardHolderList = cardHolderRepository.findCardHoldersFilteredByInsuranceDate(startDate, endDate);

        if (!optionalCardHolderList.isPresent()){
            return null;
        }

        optionalCardHolderList.get().forEach(cardHolder -> {

            cardHolderDTOList.add(cardHolderMapper.toDto(cardHolder));
        });

        return cardHolderDTOList;
    }

    @Override
    public List<CardHolderDTO> getCardHoldersExpiringToday() {

        LocalDate today = LocalDate.now();
        Optional<List<CardHolder>> optionalCardHolderList = cardHolderRepository.findCardHoldersByInsuranceDateExpiringToday(today);

        if(optionalCardHolderList.isEmpty()){
            return null;
        }

        List<CardHolderDTO> cardHolderDTOList = new ArrayList<>();

        optionalCardHolderList.get().forEach(cardHolder -> {
            cardHolderDTOList.add(cardHolderMapper.toDto(cardHolder));
        });

        return cardHolderDTOList;
    }

    @Override
    @Transactional
    public CardHolder findCardHolderById(Long id) {

        Optional<CardHolder> optionalCardHolder = cardHolderRepository.findById(id);

        if (optionalCardHolder.isPresent()){
            saveCurrentEventToDatabase(String.format("search for employee with badge id [%s]", optionalCardHolder.get().getBadgeId()));
            return optionalCardHolder.get();
        }

        return null;
    }

    @Override
    public CardHolder updateCardHolder(CardHolder cardHolder) {

        saveCurrentEventToDatabase(String.format("updating employee. name [%s]",
                                   cardHolder.getFirstName().concat(" ").concat(cardHolder.getLastName())));

        return cardHolderRepository.save(cardHolder);
    }

    @Override
    @Transactional
    public void deleteCardHolder(long id) {

        Optional<CardHolder> optionalCardHolder = cardHolderRepository.findById(id);
        saveCurrentEventToDatabase(String.format("deleting employee [%s] from the system", optionalCardHolder.get().getBadgeId()));

        cardHolderRepository.deleteById(id);
    }

    private void saveCurrentEventToDatabase(String event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserId = (String) authentication.getPrincipal();

        AppUserDTO appUserDTO = appUserService.loadUserByBadgeId(loggedUserId);

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName(appUserDTO.getFirstName().concat(" ").concat(appUserDTO.getLastName()))
                .event(event)
                .build());
    }
}
