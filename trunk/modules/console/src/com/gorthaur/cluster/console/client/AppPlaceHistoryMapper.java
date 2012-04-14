package com.gorthaur.cluster.console.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;
import com.gorthaur.cluster.console.client.activities.ManageWebAppPlace;

@WithTokenizers({
	ManageNodesPlace.Tokenizer.class,
	ManageWebAppPlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
