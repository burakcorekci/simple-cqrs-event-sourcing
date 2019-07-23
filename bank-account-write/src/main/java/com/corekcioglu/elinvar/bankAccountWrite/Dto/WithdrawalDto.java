package com.corekcioglu.elinvar.bankAccountWrite.Dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WithdrawalDto implements IDto{
    private UUID accountId;
    private Double amount;

    @Override
    public boolean isNotValid() {
        return accountId.toString().isEmpty() || amount.isNaN() || amount.isInfinite() || amount < 0;
    }
}
