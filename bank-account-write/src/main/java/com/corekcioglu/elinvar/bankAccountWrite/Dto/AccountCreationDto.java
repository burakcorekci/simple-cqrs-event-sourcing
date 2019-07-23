package com.corekcioglu.elinvar.bankAccountWrite.Dto;

import lombok.Data;

@Data
public class AccountCreationDto implements IDto {
    private String name;
    private String email;

    @Override
    public boolean isNotValid() {
        return name.isEmpty() || email.isEmpty();
    }
}
