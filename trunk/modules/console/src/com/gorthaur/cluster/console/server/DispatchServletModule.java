package com.gorthaur.cluster.console.server;

import net.customware.gwt.dispatch.server.guice.GuiceSecureDispatchServlet;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

import com.google.inject.servlet.ServletModule;
import com.gorthaur.cluster.console.server.security.AuthFilter;
import com.gorthaur.cluster.console.server.security.DefaultSecureSessionValidator;

public class DispatchServletModule extends ServletModule {

    @Override
    protected void configureServlets() {    
    	System.out.println("Binding dispatcher to: /editor/dispatch");
        serve("/console/dispatch").with(GuiceSecureDispatchServlet.class);
        bind(SecureSessionValidator.class).to(DefaultSecureSessionValidator.class);

        filter("/*").through(AuthFilter.class);
    }
}