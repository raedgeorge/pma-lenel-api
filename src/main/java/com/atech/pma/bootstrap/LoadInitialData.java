package com.atech.pma.bootstrap;

import com.atech.pma.entity.mysql.AppUser;
import com.atech.pma.repository.mysql.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LoadInitialData implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Optional<AppUser> optionalAppUser = appUserRepository.getAppUserByBadgeId("sa");

        if (!optionalAppUser.isPresent()){

            AppUser appUser = new AppUser();
            appUser.setBadgeId("sa");
            appUser.setRole("admin");
            appUser.setFirstName("system account");
            appUser.setLastName("pma lenel administrator");
            appUser.setPassword(passwordEncoder.encode("B@t@1234$$"));

            appUserRepository.save(appUser);
        }
    }
}
