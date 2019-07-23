package com.corekcioglu.elinvar.bankAccountRead.Service;

import com.corekcioglu.elinvar.bankAccountRead.Entity.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    Optional<Account> getAccountById(UUID accountId);
    Optional<Account> getAccountByEmail(String email);
    List<Account> getAccountsByName(String name);
}
