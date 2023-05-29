package com.atech.pma.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static com.atech.pma.constants.AppStaticData.AUTH_HEADER;
import static com.atech.pma.constants.AppStaticData.SECRET_KEY;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Slf4j
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                   throws ServletException, IOException {

        if (!request.getServletPath().equals("/ws")){

        String authToken = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasLength(authToken)) {

            try {

                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(authToken)
                        .getBody();

                String username = String.valueOf(claims.get("username"));
                Object authorities = claims.get("authorities");

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, null, AuthorityUtils.createAuthorityList(String.valueOf(authorities)));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException exception) {
                response.addHeader("Expired", "true");
                return;
            }
        }

            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/users/login") ||
//               request.getServletPath().equals("/api/users/logout") ||
               request.getServletPath().equals("/api/users/register");
    }
}
