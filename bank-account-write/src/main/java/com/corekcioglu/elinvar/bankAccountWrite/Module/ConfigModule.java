package com.corekcioglu.elinvar.bankAccountWrite.Module;

import com.corekcioglu.elinvar.bankAccountWrite.Config.ConfigurationFactory;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ConfigurationFactory.class);
    }


}
