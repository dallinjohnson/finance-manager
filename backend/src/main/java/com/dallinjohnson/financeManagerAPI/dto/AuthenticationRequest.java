package com.dallinjohnson.financeManagerAPI.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
