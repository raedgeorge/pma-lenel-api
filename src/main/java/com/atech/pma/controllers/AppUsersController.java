package com.atech.pma.controllers;

import com.atech.pma.entity.mysql.EventHistory;
import com.atech.pma.model.AppUserDTO;
import com.atech.pma.model.PasswordReset;
import com.atech.pma.model.WebResponseDTO;
import com.atech.pma.service.AppUserService;
import com.atech.pma.service.EventHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class AppUsersController {

    private final AppUserService appUserService;
    private final EventHistoryService eventHistoryService;


    /**
     * @return list of all users
     */
    @GetMapping("/list")
    public ResponseEntity<List<AppUserDTO>> getAllUsers(){

        return ResponseEntity.ok().body(appUserService.loadAllUsers());
    }


    /**
     *
     * @param badgeId of user to be deleted
     * @return string
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserByBadgeId(@RequestParam String badgeId){

        appUserService.deleteUserByBadgeId(badgeId);

        return ResponseEntity.accepted().body("deleted");
    }

    /**
     * register new user
     * @param appUserDTO app user to be registered
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerNewAppUser(@RequestBody AppUserDTO appUserDTO){

        if (appUserService.registerNewUser(appUserDTO)){
            return ResponseEntity.ok().body("user registration complete");
        }

       return ResponseEntity.badRequest().body("user already registered");
    }


    /**
     *
     * @param authentication authentication object
     * @return app user DTO
     */
    @GetMapping("/login")
    public ResponseEntity<?> getUserDetailsAfterLogin(Authentication authentication){

        String badgeId = authentication.getName();
        AppUserDTO appUserDTO = appUserService.loadUserByBadgeId(badgeId);

        if (!ObjectUtils.isEmpty(appUserDTO)){
            EventHistory eventHistory  = new EventHistory();
            eventHistory.setEvent("user logged in");
            eventHistory.setUserName(appUserDTO.getFirstName().concat(" ".concat(appUserDTO.getLastName())));
            eventHistoryService.saveNewEvent(eventHistory);

            return ResponseEntity.ok().body(appUserDTO);
        }

        return ResponseEntity.badRequest().body("invalid username or password");
    }

    /**
     *
     * @return boolean
     */
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(){

        String username = "any";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            username = (String) authentication.getPrincipal();
        }

        EventHistory eventHistory  = new EventHistory();
        eventHistory.setEvent("user logged out");
        eventHistory.setUserName(username);
        eventHistoryService.saveNewEvent(eventHistory);

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(true);
    }

    /**
     *
     * @param appUserDTO request param
     * @return web response DTO
     */
    @PostMapping("/password-reset")
    public ResponseEntity<WebResponseDTO> resetUserPassword(@RequestBody AppUserDTO appUserDTO,
                                                            @RequestParam("adminId") String adminId){

        WebResponseDTO webResponseDTO = appUserService.changeUserPasswordByAdmin(appUserDTO, adminId);

        return switch (webResponseDTO.getStatus()) {
            case 202 -> ResponseEntity.accepted().body(webResponseDTO);
            case 400 -> ResponseEntity.badRequest().body(webResponseDTO);
            default -> ResponseEntity.badRequest().build();
        };
    }

    @PostMapping("/user/password-reset")
    public ResponseEntity<WebResponseDTO> resetNormalUserPassword(@RequestBody PasswordReset passwordReset,
                                                            @RequestParam("badgeId") String badgeId){

        WebResponseDTO webResponseDTO = appUserService.changeUserPassword(passwordReset, badgeId);

        return switch (webResponseDTO.getStatus()) {
            case 202 -> ResponseEntity.accepted().body(webResponseDTO);
            case 400 -> ResponseEntity.badRequest().body(webResponseDTO);
            default -> ResponseEntity.badRequest().build();
        };
    }
}
