package com.corekcioglu.elinvar.bankAccountWrite.Module;

import com.corekcioglu.elinvar.bankAccountWrite.Controller.AccountController;
import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {
    public void configure() {
        bind(AccountController.class);
    }
}
