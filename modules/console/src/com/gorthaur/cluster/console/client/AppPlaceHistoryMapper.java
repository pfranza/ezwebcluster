package com.gorthaur.cluster.console.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.gorthaur.cluster.console.client.activities.ManageNodesPlace;

@WithTokenizers({
	ManageNodesPlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
