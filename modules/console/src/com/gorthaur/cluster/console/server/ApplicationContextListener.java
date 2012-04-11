package com.gorthaur.cluster.console.server;

import net.customware.gwt.dispatch.server.guice.ServerDispatchModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.gorthaur.cluster.console.server.actions.ActionsModule;

public class ApplicationContextListener extends GuiceServletContextListener {

	private static Injector INJECTOR;
	
	@Override
	protected Injector getInjector() {
		if(INJECTOR == null) {
			System.out.println("Creating new injector");
			INJECTOR = Guice.createInjector();
		}
		
		return INJECTOR.createChildInjector(new ServerDispatchModule(),
				new DispatchServletModule(), 
				new ActionsModule());
	}
	
	public static void setInjector(Injector iNJECTOR) {
		INJECTOR = iNJECTOR;
	}

}
