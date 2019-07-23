package com.corekcioglu.elinvar.bankAccountWrite.Service;

import com.corekcioglu.elinvar.bankAccountWrite.Dto.AccountCreationDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.DepositDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.TransferDto;
import com.corekcioglu.elinvar.bankAccountWrite.Dto.WithdrawalDto;
import com.corekcioglu.elinvar.commons.Entity.Event.BankAccountCreatedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.DepositPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.TransferPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.WithdrawalPerformedEvent;

import java.util.Optional;

public interface AccountService {
    Optional<BankAccountCreatedEvent> createAccount(AccountCreationDto accountCreationDto) throws Exception;
    Optional<DepositPerformedEvent> depositToAccount(DepositDto depositDto) throws Exception;
    Optional<TransferPerformedEvent> transferToAnotherAccount(TransferDto transferDto) throws Exception;
    Optional<WithdrawalPerformedEvent> withdrawFromAccount(WithdrawalDto withdrawalDto) throws Exception;
}
