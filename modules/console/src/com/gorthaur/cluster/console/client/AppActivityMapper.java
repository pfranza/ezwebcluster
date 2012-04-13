package com.gorthaur.cluster.console.client;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.gorthaur.cluster.console.client.activities.ManageNodes;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;

public class AppActivityMapper implements ActivityMapper {

	@Inject
	Provider<ManageNodes> manageNodesActivity;
	
	@Override
	public Activity getActivity(Place place) {
		
		if(place instanceof ManageNodesPlace) {
			return manageNodesActivity.get();
		}
		
		System.out.println("No Activity Mapped For Place: " + place);
		return null;
	}
	
	

}
