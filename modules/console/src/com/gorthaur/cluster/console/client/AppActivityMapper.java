package com.gorthaur.cluster.console.client;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.gorthaur.cluster.console.client.activities.ManageNodes;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;
import com.gorthaur.cluster.console.client.activities.ManageWebApp;
import com.gorthaur.cluster.console.client.activities.ManageWebAppPlace;

public class AppActivityMapper implements ActivityMapper {

	@Inject
	Provider<ManageNodes> manageNodesActivity;
	
	@Inject
	Provider<ManageWebApp> manageWebApp;
	
	@Override
	public Activity getActivity(Place place) {
		
		if(place instanceof ManageNodesPlace) {
			return manageNodesActivity.get();
		}
		
		if(place instanceof ManageWebAppPlace) {
			return manageWebApp.get();
		}
		
		System.out.println("No Activity Mapped For Place: " + place);
		return null;
	}
	
	

}
