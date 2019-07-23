package com.corekcioglu.elinvar.eventStreamProcessing.Service;

import com.corekcioglu.elinvar.commons.Entity.Event.BankAccountCreatedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.DepositPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.TransferPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.WithdrawalPerformedEvent;
import com.corekcioglu.elinvar.eventStreamProcessing.Entity.Account;

public interface AccountService {
    Account create(Account account);
    Account update(Account account);

    Account processEvent(BankAccountCreatedEvent event) throws Exception;
    Account processEvent(DepositPerformedEvent event) throws Exception;
    Account processEvent(TransferPerformedEvent event) throws Exception;
    Account processEvent(WithdrawalPerformedEvent event) throws Exception;
}
