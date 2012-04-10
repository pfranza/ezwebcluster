package com.gorthaur.cluster.console.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ManageNodesPlace extends Place {

	private String token;
	
	public ManageNodesPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ManageNodesPlace> {
		@Override
		public String getToken(ManageNodesPlace place) {
			return place.token;
		}

		@Override
		public ManageNodesPlace getPlace(String token) {
			return new ManageNodesPlace(token);
		}
	}
	
}