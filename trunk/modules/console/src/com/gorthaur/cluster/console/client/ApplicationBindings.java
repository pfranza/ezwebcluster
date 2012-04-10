package com.gorthaur.cluster.console.client;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;

public class ApplicationBindings extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).toProvider(EventBusProvider.class).in(Singleton.class);
		bind(PlaceController.class).toProvider(PlaceControllerProvider.class).in(Singleton.class);
		bind(SecureSessionAccessor.class).to(DefaultSecureSessionAccessor.class);
		install(new SecureDispatchModule()); 
	}
	
	@Singleton
	public static class PlaceControllerProvider implements Provider<PlaceController> {

		private PlaceController pc;
		
		@SuppressWarnings("deprecation")
		@Inject
		public PlaceControllerProvider(EventBus eventBus) {
			pc = new PlaceController(eventBus);
			System.out.println("Creating Place Controller");
		}
		
		@Override
		public PlaceController get() {
			return pc;
		}
		
	}
	
	@Singleton
	public static class EventBusProvider implements Provider<EventBus> {

		private SimpleEventBus bus = new SimpleEventBus();

		{
			System.out.println("Created EventBus");
		}
		
		@Override
		public EventBus get() {
			return bus;
		}
		
	}

}
