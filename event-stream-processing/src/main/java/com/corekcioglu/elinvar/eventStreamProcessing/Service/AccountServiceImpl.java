package com.corekcioglu.elinvar.eventStreamProcessing.Service;

import com.corekcioglu.elinvar.commons.Entity.Event.BankAccountCreatedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.DepositPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.TransferPerformedEvent;
import com.corekcioglu.elinvar.commons.Entity.Event.WithdrawalPerformedEvent;
import com.corekcioglu.elinvar.eventStreamProcessing.Entity.Account;
import com.corekcioglu.elinvar.eventStreamProcessing.Repository.AccountRepository;

import javax.inject.Inject;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        return accountRepository.create(account);
    }

    public Account update(Account account) {
        return accountRepository.update(account);
    }

    public Account processEvent(BankAccountCreatedEvent event) {
        Account account = new Account();
        account.setId(event.getAccountId());
        account.setName(event.getName());
        account.setEmail(event.getEmail());
        account.setAmount(0.0);
        return accountRepository.create(account);
    }

    public Account processEvent(DepositPerformedEvent event) throws Exception {
        Account account = accountRepository.findById(event.getAccountId());
        if (account == null) {
            throw new Exception();
        }

        account.setAmount(account.getAmount() + event.getAmount());
        accountRepository.update(account);
        return account;
    }

    public Account processEvent(WithdrawalPerformedEvent event) throws Exception {
        Account account = accountRepository.findById(event.getAccountId());
        if (account == null) {
            throw new Exception();
        }

        if (account.getAmount() < event.getAmount()) {
            throw new Exception();
        }

        account.setAmount(account.getAmount() - event.getAmount());
        accountRepository.update(account);
        return account;
    }

    public Account processEvent(TransferPerformedEvent event) throws Exception {
        Account account1 = accountRepository.findById(event.getAccountId());
        if (account1 == null) {
            throw new Exception();
        }

        Account account2 = accountRepository.findById(event.getTargetId());
        if (account2 == null) {
            throw new Exception();
        }

        if (account1.getAmount() < event.getAmount()) {
            throw new Exception();
        }

        account1.setAmount(account1.getAmount() - event.getAmount());
        account2.setAmount(account2.getAmount() + event.getAmount());
        accountRepository.update(account1);
        accountRepository.update(account2);

        return account1;
    }
}
