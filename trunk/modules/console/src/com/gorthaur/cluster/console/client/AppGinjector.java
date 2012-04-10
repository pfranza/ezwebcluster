package com.gorthaur.cluster.console.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;

@GinModules(ApplicationBindings.class)
public interface AppGinjector extends Ginjector {


	EventBus getEventBus();
	PlaceController getPlaceController();
	
	AppActivityMapper getAppActivityMapper();
	AppPlaceHistoryMapper getAppPlaceHistoryMapper();

}
