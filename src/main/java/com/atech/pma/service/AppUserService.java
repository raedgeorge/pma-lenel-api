package com.atech.pma.service;

import com.atech.pma.model.AppUserDTO;
import com.atech.pma.model.WebResponseDTO;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */
public interface AppUserService {

    AppUserDTO loadUserByBadgeId(String badgeId);

    boolean registerNewUser(AppUserDTO appUserDTO);

    List<AppUserDTO> loadAllUsers();

    void deleteUserByBadgeId(String badgeId);

    WebResponseDTO changeUserPassword(AppUserDTO appUserDTO, String adminId);
}
