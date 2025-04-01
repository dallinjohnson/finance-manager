package com.dallinjohnson.financeManagerAPI.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TransactionFilterDTO {
    private Boolean isCredit;
    private LocalDate dateAfter;
    private LocalDate dateBefore;
    private Long categoryId;
    private Long accountId;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String descriptionContains;

    @AssertTrue(message = "dateAfter must be <= dateBefore if both are provided")
    public boolean isValidDateRange() {
        if (dateAfter == null || dateBefore == null) return true;
        return !dateAfter.isAfter(dateBefore);
    }

    @AssertTrue(message = "amountFrom from must be <= amountTo if both are provided")
    public boolean isValidAmountRange() {
        if(amountFrom == null || amountTo == null) return true;
        int comparison = amountFrom.compareTo(amountTo);
        return comparison <= 0;
    }

    @AssertTrue(message = "Amount values must be non-negative")
    public boolean areAmountsNonNegative() {
        return (amountFrom == null || amountFrom.compareTo(BigDecimal.ZERO) >= 0) &&
                (amountTo == null || amountTo.compareTo(BigDecimal.ZERO) >= 0);
    }
}
