package com.corekcioglu.elinvar.bankAccountRead;

import com.corekcioglu.elinvar.bankAccountRead.Config.GuiceContextListener;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class BankAccountRead {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8001);
        ServletContextHandler handler =
                new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        GuiceContextListener contextListener = new GuiceContextListener(BankAccountRead.class.getSimpleName());
        handler.addEventListener(contextListener);
        handler.addServlet(HttpServletDispatcher.class, "/*");
        server.start();
    }
}
