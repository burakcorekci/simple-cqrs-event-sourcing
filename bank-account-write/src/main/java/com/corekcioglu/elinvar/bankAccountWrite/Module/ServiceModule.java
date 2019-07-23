package com.corekcioglu.elinvar.bankAccountWrite.Module;

import com.corekcioglu.elinvar.bankAccountWrite.Service.AccountService;
import com.corekcioglu.elinvar.bankAccountWrite.Service.AccountServiceImpl;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
