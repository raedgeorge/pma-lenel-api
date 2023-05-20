package com.atech.pma.service.impl;

import com.atech.pma.entity.mysql.AppUser;
import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.mappers.AppUserMapper;
import com.atech.pma.model.AppUserDTO;
import com.atech.pma.model.WebResponseDTO;
import com.atech.pma.repository.mysql.AppUserRepository;
import com.atech.pma.service.AppUserService;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Slf4j
@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final EventHistoryService eventHistoryService;


    @Override
    @Transactional
    public AppUserDTO loadUserByBadgeId(String badgeId) {

        Optional<AppUser> optionalAppUser = appUserRepository.getAppUserByBadgeId(badgeId);

        if (!optionalAppUser.isPresent()){
            log.info("user with badge Id [{}] do not exists", badgeId);

            logUserRegistrationFailedEventToDatabase(String.format("ERROR. user with badge Id [%s] do not exists", badgeId));
            return null;
        }

        return appUserMapper.toDto(optionalAppUser.get());
    }

    @Override
    @Transactional
    public void deleteUserByBadgeId(String badgeId) {

        appUserRepository.deleteAppUserByBadgeId(badgeId);
        log.info("user with badge ID {} deleted", badgeId);
        saveCurrentEventToDatabase(String.format("user with badge ID [%s] deleted", badgeId));
    }

    @Override
    @Transactional
    public boolean registerNewUser(AppUserDTO appUserDto) {

        AppUserDTO appUserDTO = loadUserByBadgeId(appUserDto.getBadgeId());
        if (!ObjectUtils.isEmpty(appUserDTO)){
            return false;
        }

        appUserDto.setFirstName(appUserDto.getFirstName().substring(0, 1).toUpperCase().concat(appUserDto.getFirstName().substring(1)));
        appUserDto.setLastName(appUserDto.getLastName().substring(0, 1).toUpperCase().concat(appUserDto.getLastName().substring(1)));

        appUserDto.setPassword(passwordEncoder.encode(appUserDto.getPassword()));

        appUserDto.setRole("user");
        AppUser appUser = appUserMapper.toEntity(appUserDto);
        appUserRepository.save(appUser);

        saveUserRegistrationEventToDatabase(String.format("new user registered to the system. User Id [%s]",
                                            appUserDto.getBadgeId()),
                                            appUserDto);

        return true;
    }

    @Override
    public List<AppUserDTO> loadAllUsers() {

        List<AppUserDTO> appUserDTOList = new ArrayList<>();

        appUserRepository.findAll().forEach((appUser -> {
            appUserDTOList.add(appUserMapper.toDto(appUser));
        }));

        saveCurrentEventToDatabase("get a list of all application users");

        return appUserDTOList;
    }


    @Override
    public WebResponseDTO changeUserPassword(AppUserDTO appUserDTO, String adminId) {

        WebResponseDTO webResponseDTO = WebResponseDTO.builder().build();

        Optional<AppUser> optionalAdmin = appUserRepository.getAppUserByBadgeId(adminId);
        if (!optionalAdmin.isPresent()){

            return WebResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("admin user not found or user requesting resource is not admin!")
                        .build();
        }

        AppUser adminUser = optionalAdmin.get();

        if (adminUser.getRole().equalsIgnoreCase("admin")){

            Optional<AppUser> optionalAppUser = appUserRepository.getAppUserByBadgeId(appUserDTO.getBadgeId());

            if (!optionalAppUser.isPresent()) {

                webResponseDTO = WebResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("user is not found in the system!")
                        .build();

                saveCurrentEventToDatabase(String.format("ERROR. user change password request failed. user with Id [%s] does not exist", appUserDTO.getBadgeId()));

                return webResponseDTO;
            }

            AppUser appUser = optionalAppUser.get();

            appUser.setPassword(appUserDTO.getPassword() != null ?
                                passwordEncoder.encode(appUserDTO.getPassword()) : appUser.getPassword());

            appUser.setFirstName(appUserDTO.getFirstName());
            appUser.setLastName(appUserDTO.getLastName());
            appUser.setRole(appUserDTO.getRole().toLowerCase());
            appUser.setBadgeId(appUserDTO.getBadgeId());

            appUserRepository.save(appUser);

            webResponseDTO = WebResponseDTO.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("user edit success")
                .build();

//            if (passwordEncoder.matches(appUserDTO.getPassword(), appUser.getPassword())) {
//
//                appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
//                appUserRepository.save(appUser);
//
//                webResponseDTO = WebResponseDTO.builder()
//                        .status(HttpStatus.ACCEPTED.value())
//                        .message("password changed successfully")
//                        .build();
//
//                saveCurrentEventToDatabase(String.format("password changed for user with Id [%s]", appUserDTO.getBadgeId()));
//            } else {
//                webResponseDTO = WebResponseDTO.builder()
//                        .status(HttpStatus.BAD_REQUEST.value())
//                        .message("old password is wrong!")
//                        .build();
//
//                saveCurrentEventToDatabase(
//                        String.format("ERROR. user change password request failed for user Id [%s]. old password is not correct",
//                                appUserDTO.getBadgeId()));
//            }
        }

        return webResponseDTO;
    }

    private void saveCurrentEventToDatabase(String event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedUserId = (String) authentication.getPrincipal();

        AppUser appUser = appUserRepository.getAppUserByBadgeId(loggedUserId).get();

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName(appUser.getFirstName().concat(" ").concat(appUser.getLastName()))
                .event(event)
                .build());
    }

    private void saveUserRegistrationEventToDatabase(String event, AppUserDTO appUserDTO) {

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName(appUserDTO.getFirstName().concat(" ").concat(appUserDTO.getLastName()))
                .event(event)
                .build());
    }

    private void logUserRegistrationFailedEventToDatabase(String event) {

        eventHistoryService.saveNewEvent(EventHistory.builder()
                .userName("anonymous")
                .event(event)
                .build());
    }
}
