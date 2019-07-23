package com.corekcioglu.elinvar.eventStreamProcessing.Module;

import com.corekcioglu.elinvar.eventStreamProcessing.Service.AccountService;
import com.corekcioglu.elinvar.eventStreamProcessing.Service.AccountServiceImpl;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
