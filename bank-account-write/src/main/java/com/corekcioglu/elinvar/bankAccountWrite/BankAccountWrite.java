package com.corekcioglu.elinvar.bankAccountWrite;

import com.corekcioglu.elinvar.bankAccountWrite.Config.ConfigurationFactory;
import com.corekcioglu.elinvar.bankAccountWrite.Config.GuiceContextListener;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class BankAccountWrite {
    public static void main(String[] args) throws Exception {
        ConfigurationFactory configFactory = new ConfigurationFactory();

        Server server = new Server(8000);
        ServletContextHandler handler =
                new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        GuiceContextListener contextListener = new GuiceContextListener(configFactory);
        handler.addEventListener(contextListener);
        handler.addServlet(HttpServletDispatcher.class, "/*");
        server.start();
    }
}
