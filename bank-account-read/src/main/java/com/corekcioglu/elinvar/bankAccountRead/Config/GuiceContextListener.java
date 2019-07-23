package com.corekcioglu.elinvar.bankAccountRead.Config;

import com.corekcioglu.elinvar.bankAccountRead.Module.*;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class GuiceContextListener extends GuiceResteasyBootstrapServletContextListener {
    private final String persistenceUnit;

    public GuiceContextListener(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    protected List<Module> getModules(final ServletContext context) {
        final List<Module> result = new ArrayList<>();

        result.add(new PersistenceModule(this.persistenceUnit));
        result.add(new ServiceModule());
        result.add(new ControllerModule());

        return result;
    }
}
