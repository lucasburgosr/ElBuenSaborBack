package com.example.buensaborback.config.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (jwtAuthToken != null) {
            String token = jwtAuthToken.getToken().getTokenValue();
            System.out.println("JWT Token: " + token); // Log the token

            // Log roles
            jwtAuthToken.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
        }
        filterChain.doFilter(request, response);
    }

}