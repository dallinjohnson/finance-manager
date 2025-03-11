package com.dallinjohnson.financeManagerAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountDTO(
        @NotBlank(message = "Account name is required")
        @Size(max = 100, message = "Account name must be less than 100 characters")
        String name
) { }
