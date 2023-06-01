package com.atech.pma.config;

import com.atech.pma.security.filters.JwtTokenGeneratorFilter;
import com.atech.pma.security.filters.JwtTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author raed abu Sa'da
 * on 07/04/2023
 */

@Configuration
public class AppSecurityConfig {

    @Value("${spring.cors.url}")
    private String corsUrl;

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://192.168.1.208:8080", "http://192.168.1.3:4200"));
//                corsConfiguration.setAllowedOrigins(Collections.singletonList(corsUrl));
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Expired"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);

                return corsConfiguration;
            }
        });

        http.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .requestMatchers("/swagger-ui.html/**").permitAll()
                .requestMatchers("/api/employee/image/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/employees/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/excel/upload").authenticated()
                .requestMatchers(HttpMethod.GET,"/api/users/logout").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/users/delete").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/users/password-reset").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/users/list").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/card-holders/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/reports/**").authenticated()
                .requestMatchers(HttpMethod.GET,"/api/cars/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/events/**").authenticated();

        http.authorizeHttpRequests().anyRequest().authenticated();

        http.formLogin().and().httpBasic();

        return http.build();
    }
}
