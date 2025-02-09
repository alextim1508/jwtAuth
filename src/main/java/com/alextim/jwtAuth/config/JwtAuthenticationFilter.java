package com.alextim.jwtAuth.config;

import com.alextim.jwtAuth.exception.JwtTokenException;
import com.alextim.jwtAuth.service.JwtService;
import com.alextim.jwtAuth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());

        String extractedUserName;
        try {
            extractedUserName = jwtService.extractUserName(jwt);
            log.info("extractedUserName: {}", extractedUserName);
        } catch (Exception e) {
            throw new JwtTokenException(e);
        }


        if (StringUtils.isNotEmpty(extractedUserName) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(extractedUserName);
            log.info("loaded user: {}", userDetails);

            boolean tokenValid;
            try {
                tokenValid = jwtService.isTokenValid(jwt, userDetails);
                log.info("isTokenValid: {}", tokenValid);
            } catch (Exception e) {
                throw new JwtTokenException(e);
            }

            if (tokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                setSecurityContext(authToken);

                log.info("Successful authentication");
            } else {
                log.info("Failed  authentication");
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(UsernamePasswordAuthenticationToken authToken) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
