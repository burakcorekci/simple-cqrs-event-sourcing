package com.corekcioglu.elinvar.bankAccountRead.Module;

import com.corekcioglu.elinvar.bankAccountRead.Config.JpaInitializer;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class PersistenceModule extends AbstractModule {
    private final String persistenceUnit;

    public PersistenceModule(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(persistenceUnit));
        bind(JpaInitializer.class).asEagerSingleton();
    }
}