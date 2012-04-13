package com.gorthaur.cluster.console.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;

public class ConsoleEntryPoint implements EntryPoint {

	private final AppGinjector injector = GWT.create(AppGinjector.class);
	
	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {
		EventBus eventBus = injector.getEventBus();
        PlaceController placeController = injector.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        AppActivityMapper activityMapper = injector.getAppActivityMapper();
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(new AcceptsOneWidget() {
			
        	SimplePanel p = new SimplePanel();
        	
        	{
        		RootLayoutPanel.get().add(p);
        	}
        	
			@Override
			public void setWidget(IsWidget w) {
				p.setWidget(w);
			}
		});

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= injector.getAppPlaceHistoryMapper();
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, new ManageNodesPlace(""));
       
       // .add(container);
        historyHandler.handleCurrentHistory();
        
	}

}
