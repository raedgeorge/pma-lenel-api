package com.atech.pma.security.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.atech.pma.constants.AppStaticData.AUTH_HEADER;
import static com.atech.pma.constants.AppStaticData.SECRET_KEY;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    public static final int THIRTY_MINUTES = 1800000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){

            SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setIssuer("LENEL")
                    .setSubject("Access Token")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + THIRTY_MINUTES))
                    .claim("username", authentication.getName())
                    .claim("authorities", authentication.getAuthorities())
                    .signWith(secretKey)
                    .compact();

            response.setHeader(AUTH_HEADER, token);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/users/login");
    }
}
