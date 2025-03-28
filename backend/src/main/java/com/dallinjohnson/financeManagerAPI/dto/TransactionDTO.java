package com.dallinjohnson.financeManagerAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDTO(

        @NotNull(message = "Transaction date is required")
        LocalDate date,

        boolean isCredit,

        @NotNull(message = "Transaction amount is required")
        @DecimalMin(value = "0.01", message = "Transaction amount must be at least 0.01")
        @DecimalMax(value = "1000000.00", message = "Transaction amount cannot exceed 1,000,000.00")
        BigDecimal amount,

        @Size(max = 255, message = "Transaction description cannot exceed 255 characters")
        String description,

        @Min(value = 0, message = "Category ID must be a non-negative number")
        Long categoryId,

        @Min(value = 0, message = "Account ID must be a non-negative number")
        Long accountId
) { }