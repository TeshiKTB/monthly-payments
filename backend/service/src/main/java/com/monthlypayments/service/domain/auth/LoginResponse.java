package com.monthlypayments.service.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private Instant expiresAt;
}
