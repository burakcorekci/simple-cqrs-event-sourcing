package com.corekcioglu.elinvar.bankAccountRead.Module;

import com.corekcioglu.elinvar.bankAccountRead.Service.AccountService;
import com.corekcioglu.elinvar.bankAccountRead.Service.AccountServiceImpl;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
