package com.example.buensaborback.config.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

// Implementación personalizada de OAuth2TokenValidator para validar el público objetivo (audience) de JWT
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String audience; // Valor esperado del público objetivo

    // Constructor para inicializar con el público objetivo esperado
    public AudienceValidator(String audience) {
        this.audience = audience;
    }

    // Método para validar el público objetivo del JWT
    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2Error error = new OAuth2Error("invalid_token", "El público objetivo requerido no está presente", null);

        // Verifica si el público objetivo del JWT contiene el valor esperado
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
