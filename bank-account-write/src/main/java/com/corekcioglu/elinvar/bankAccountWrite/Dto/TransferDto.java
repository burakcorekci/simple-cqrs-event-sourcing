package com.corekcioglu.elinvar.bankAccountWrite.Dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TransferDto implements IDto{
    private UUID accountId;
    private UUID targetId;
    private Double amount;

    @Override
    public boolean isNotValid() {
        return accountId.toString().isEmpty() || targetId.toString().isEmpty() ||
                amount.isNaN() || amount.isInfinite() || amount < 0;
    }
}
