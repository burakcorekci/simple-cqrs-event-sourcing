package com.corekcioglu.elinvar.bankAccountWrite.Config;

import com.corekcioglu.elinvar.bankAccountWrite.Module.ConfigModule;
import com.corekcioglu.elinvar.bankAccountWrite.Module.ControllerModule;
import com.corekcioglu.elinvar.bankAccountWrite.Module.KafkaModule;
import com.corekcioglu.elinvar.bankAccountWrite.Module.ServiceModule;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class GuiceContextListener extends GuiceResteasyBootstrapServletContextListener {
    private ConfigurationFactory configFactory;

    public GuiceContextListener(ConfigurationFactory configFactory) {
        this.configFactory = configFactory;
    }

    @Override
    protected List<Module> getModules(final ServletContext context) {
        final List<Module> result = new ArrayList<>();

        result.add(new ConfigModule());
        result.add(new KafkaModule(configFactory));
        result.add(new ControllerModule());
        result.add(new ServiceModule());

        return result;
    }
}
