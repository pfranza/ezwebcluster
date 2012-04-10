package com.gorthaur.cluster.console.client.activities;

import javax.inject.Inject;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gorthaur.cluster.console.client.AppPlaceHistoryMapper;
import com.gorthaur.cluster.console.client.shared.ClusterNode;
import com.gorthaur.cluster.console.client.shared.ListNodes;
import com.gorthaur.cluster.console.client.shared.NodeCollection;

public class ManageNodes extends AbstractCompositeActivity {

	private static ManageNodesUiBinder uiBinder = GWT
			.create(ManageNodesUiBinder.class);

	interface ManageNodesUiBinder extends UiBinder<Widget, ManageNodes> {
	}
	
	@Inject
	DispatchAsync dispatcher;
	
	@Inject
	AppPlaceHistoryMapper historyMapper;
	
	@UiField VerticalPanel machineList; 
	@UiField SpanElement machineName;
	
	private AsyncCallback<NodeCollection> handler  = new AsyncCallback<NodeCollection>() {
		
		@Override
		public void onSuccess(NodeCollection result) {
			machineList.clear();
			for(ClusterNode c: result.getNodes()) {
				System.out.println(c.getName());
				machineList.add(new Hyperlink(c.getName(), historyMapper.getToken(new ManageNodesPlace(c.getName()))));
			}
		}
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
	};
	
	

	public ManageNodes() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		dispatcher.execute(new ListNodes(), handler);
		machineName.setInnerText(((ManageNodesPlace)historyMapper.getPlace(History.getToken())).getToken());
	}

}
