package com.corekcioglu.elinvar.bankAccountRead.Module;

import com.corekcioglu.elinvar.bankAccountRead.Controller.AccountController;
import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {
    @Override
    public void configure() {
        bind(AccountController.class);
    }
}
