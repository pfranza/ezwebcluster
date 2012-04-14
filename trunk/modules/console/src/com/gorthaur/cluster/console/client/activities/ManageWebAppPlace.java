package com.gorthaur.cluster.console.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ManageWebAppPlace extends Place {

	private String token;
	
	public ManageWebAppPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ManageWebAppPlace> {
		@Override
		public String getToken(ManageWebAppPlace place) {
			return place.token;
		}

		@Override
		public ManageWebAppPlace getPlace(String token) {
			return new ManageWebAppPlace(token);
		}
	}
	
}