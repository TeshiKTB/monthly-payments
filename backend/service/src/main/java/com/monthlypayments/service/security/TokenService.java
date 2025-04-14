package com.monthlypayments.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.monthlypayments.service.domain.auth.LoginResponse;
import com.monthlypayments.service.domain.auth.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${api.auth.jwt.secretkey}")
    private String secret;

    @Value("${api.auth.jwt.validationtime}")
    private int tokenValidationTime;

    public LoginResponse generateToken(User u) {
        try {
            Instant expiresAt = getExpiresAt();
            return new LoginResponse(JWT.create().withIssuer(issuer)
                    .withSubject(u.getUsername())
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(secret)), expiresAt);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error creating JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant getExpiresAt() {
    return LocalDateTime.now().plusSeconds(tokenValidationTime).toInstant(ZoneOffset.of("-03:00"));
    }
}
