package com.gorthaur.cluster.console.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MasterLayout extends Composite implements AcceptsOneWidget {

	private static MasterLayoutUiBinder uiBinder = GWT
			.create(MasterLayoutUiBinder.class);

	interface MasterLayoutUiBinder extends UiBinder<Widget, MasterLayout> {
	}

	@UiField SimplePanel container;
	
	public MasterLayout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setWidget(IsWidget w) {
		container.setWidget(w);
	}

}
