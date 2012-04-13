package com.gorthaur.cluster.console.client.activities;

import javax.inject.Inject;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.general.StringResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gorthaur.cluster.console.client.AppPlaceHistoryMapper;
import com.gorthaur.cluster.console.client.shared.ClusterNode;
import com.gorthaur.cluster.console.client.shared.ClusterNodeInfo;
import com.gorthaur.cluster.console.client.shared.LaunchApplication;
import com.gorthaur.cluster.console.client.shared.ListNodeInfo;
import com.gorthaur.cluster.console.client.shared.ListNodes;
import com.gorthaur.cluster.console.client.shared.NodeCollection;
import com.gorthaur.cluster.console.client.shared.TerminateApplication;
import com.gorthaur.cluster.console.client.shared.TerminateNode;

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
	
	@UiField SpanElement memoryFreePercent;
	@UiField SpanElement cpuUtilization;
	
	@UiField FlexTable filesSynced;
	@UiField FlexTable applications;
	
	private AsyncCallback<NodeCollection> handler  = new AsyncCallback<NodeCollection>() {
		
		@Override
		public void onSuccess(NodeCollection result) {
			machineList.clear();
			for(ClusterNode c: result.getNodes()) {
				machineList.add(new Hyperlink(c.getName(), historyMapper.getToken(new ManageNodesPlace(c.getName()))));
			}
		}
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
	};
	
	private AsyncCallback<ClusterNodeInfo> detailsHandler = new AsyncCallback<ClusterNodeInfo>() {
		
		@Override
		public void onSuccess(ClusterNodeInfo result) {
			filesSynced.removeAllRows();
			applications.removeAllRows();
			
			memoryFreePercent.setInnerText(result.getMemoryFreePercent() + "%");
			cpuUtilization.setInnerText(result.getCpuUtilization() + "%");
			
			for (int i = 0; result.getApplicationId() != null && i < result.getApplicationId().length; i++) {
				applications.setText(i, 0, result.getName()[i]);
				applications.setText(i, 1, result.getApplicationId()[i]);
				applications.setText(i, 2, result.getStatus()[i]);
				
				Button b = new Button("Terminate");
				applications.setWidget(i, 3, b);
				final String appId = result.getApplicationId()[i];
				b.addClickHandler(new ClickHandler() {				
					@Override
					public void onClick(ClickEvent event) {
						TerminateApplication term = new TerminateApplication();
						term.setApplicationProcessId(appId);
						term.setNodeAddress(machineName.getInnerText());
						dispatcher.execute(term, new AsyncCallback<StringResult>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
							}

							@Override
							public void onSuccess(StringResult result) {
								ManageNodesPlace place = (ManageNodesPlace)historyMapper.getPlace(History.getToken());
								if(place != null) {
									dispatcher.execute(new ListNodeInfo(place.getToken()), detailsHandler);
								}
							}
						});
					}
				});
			}
			
			for (int i = 0; result.getFilenames() != null && i < result.getFilenames().length; i++) {
				applications.setText(i, 0, result.getFilenames()[i]);
				applications.setText(i, 1, result.getChecksum()[i]);
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
		
		final ManageNodesPlace place = (ManageNodesPlace)historyMapper.getPlace(History.getToken());
		if(place != null && place.getToken().length() > 1) {
			machineName.setInnerText(place.getToken());
			dispatcher.execute(new ListNodeInfo(place.getToken()), detailsHandler);
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				@Override
				public boolean execute() {
					ManageNodesPlace currentPlace = (ManageNodesPlace)historyMapper.getPlace(History.getToken());
					if(currentPlace != null && currentPlace.getToken().equals(place.getToken())) {
						dispatcher.execute(new ListNodeInfo(place.getToken()), detailsHandler);
						return true;
					} else {
						clearData();
						return false;
					}
				}
			}, 5000);
		} else {
			clearData();
		}

	}

	private void clearData() {
		filesSynced.removeAllRows();
		applications.removeAllRows();
		
		memoryFreePercent.setInnerText("NA %");
		cpuUtilization.setInnerText("NA %");
	}
	
	@UiHandler("launchBalancer")
	void clickLaunch(ClickEvent evt) {
		dispatcher.execute(new LaunchApplication("com.gorthaur.cluster.loadbalancer.LoadBalancerApplication", machineName.getInnerText()), new AsyncCallback<StringResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(StringResult result) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("launchWebServer")
	void clickLaunchServer(ClickEvent evt) {
		dispatcher.execute(new LaunchApplication("com.gorthaur.cluster.webserver.WebServerApplication", machineName.getInnerText()), new AsyncCallback<StringResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(StringResult result) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("terminateNode")
	void clickTerminateNode(ClickEvent evt) {
		dispatcher.execute(new TerminateNode(machineName.getInnerText()), new AsyncCallback<StringResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(StringResult result) {
				dispatcher.execute(new ListNodes(), handler);
			}
		});
	}

}
