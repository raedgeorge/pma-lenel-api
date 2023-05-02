package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> getAppUserByBadgeId(String badgeId);

    void deleteAppUserByBadgeId(String badgeId);
}
