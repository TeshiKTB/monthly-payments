package com.monthlypayments.service.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monthlypayments.service.domain.auth.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @JsonProperty("username")
    @NotBlank
    private String username;

    @JsonProperty("password")
    @NotBlank
    private String password;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
