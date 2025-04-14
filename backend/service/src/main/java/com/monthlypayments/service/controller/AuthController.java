package com.monthlypayments.service.controller;

import com.monthlypayments.service.domain.auth.AuthRequest;
import com.monthlypayments.service.domain.auth.LoginResponse;
import com.monthlypayments.service.domain.auth.User;
import com.monthlypayments.service.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    TokenService tokenService;

    AuthenticationManager authenticationManager;

    public AuthController(
            TokenService tokenService,
            AuthenticationManager authenticationManager
    ) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        return ResponseEntity.ok(
                tokenService.generateToken((User) authentication.getPrincipal())
        );
    }
}
