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
        // Obtener el token de autenticación JWT del contexto de seguridad
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // Si hay un token JWT válido presente, registrar información
        if (jwtAuthToken != null) {
            String token = jwtAuthToken.getToken().getTokenValue();
            System.out.println("Token JWT: " + token); // Registrar el token JWT

            // Registrar los roles/autoridades asociados al token
            jwtAuthToken.getAuthorities().forEach(authority -> System.out.println("Autoridad: " + authority.getAuthority()));
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

}
