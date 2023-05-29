package com.atech.pma.security;

import com.atech.pma.model.AppUserDTO;
import com.atech.pma.service.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Slf4j
@Component
@AllArgsConstructor
public class AppUsernamePasswordAuthProvider implements AuthenticationProvider {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String badgeId = authentication.getName();
        String password = authentication.getCredentials().toString();

        AppUserDTO appUserDTO = appUserService.loadUserByBadgeId(badgeId);

        if (!ObjectUtils.isEmpty(appUserDTO)){

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(appUserDTO.getRole()));

            if (passwordEncoder.matches(password, appUserDTO.getPassword())){
                return new UsernamePasswordAuthenticationToken(badgeId, password, authorities);
            }
            else throw new BadCredentialsException("Invalid Password");
        }
        else throw new UsernameNotFoundException("User name not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
