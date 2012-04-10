package com.gorthaur.cluster.console.client;

import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.gorthaur.cluster.console.client.activities.ManageNodes;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;

public class AppActivityMapper implements ActivityMapper {

	@Inject
	ManageNodes manageNodesActivity;
	
	@Override
	public Activity getActivity(Place place) {

		if(place instanceof ManageNodesPlace) {
			return manageNodesActivity;
		}
		
		System.out.println("No Activity Mapped For Place: " + place);
		return null;
	}
	
	

}
