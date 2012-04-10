package com.gorthaur.cluster.console.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class ConsoleEntryPoint implements EntryPoint {

	private final AppGinjector injector = GWT.create(AppGinjector.class);
	
	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {
		EventBus eventBus = injector.getEventBus();
        PlaceController placeController = injector.getPlaceController();

        MasterLayout container = injector.getMasterLayout();
        
        // Start ActivityManager for the main widget with our ActivityMapper
        AppActivityMapper activityMapper = injector.getAppActivityMapper();
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(container);

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= injector.getAppPlaceHistoryMapper();
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, Place.NOWHERE);
       
        RootLayoutPanel.get().add(container);
        historyHandler.handleCurrentHistory();
	}

}
