package com.corekcioglu.elinvar.bankAccountRead.Service;

import com.corekcioglu.elinvar.bankAccountRead.Entity.Account;
import com.corekcioglu.elinvar.bankAccountRead.Repository.AccountRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccountById(UUID accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(account);
        }
    }

    public Optional<Account> getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(account);
        }
    }

    public List<Account> getAccountsByName(String name) {
        return accountRepository.findByName(name);
    }
}
