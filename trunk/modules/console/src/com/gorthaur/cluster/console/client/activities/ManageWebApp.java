package com.gorthaur.cluster.console.client.activities;

import javax.inject.Inject;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.general.StringResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gorthaur.cluster.console.client.activities.upload.DropHandler;
import com.gorthaur.cluster.console.client.activities.upload.File;
import com.gorthaur.cluster.console.client.activities.upload.FileBlobReader;
import com.gorthaur.cluster.console.client.activities.upload.FileEvent;
import com.gorthaur.cluster.console.client.activities.upload.FileEvent.FileEventHandler;
import com.gorthaur.cluster.console.client.shared.ReplicateFileDataAction;

public class ManageWebApp  extends AbstractCompositeActivity {

	private static ManageWebAppUiBinder uiBinder = GWT
			.create(ManageWebAppUiBinder.class);

	interface ManageWebAppUiBinder extends UiBinder<Widget, ManageWebApp> {
	}

	@UiField public Label dropField;
	
	@Inject DispatchAsync dispatcher;
	
	public ManageWebApp() {
		initWidget(uiBinder.createAndBindUi(this));
		
		DropHandler dropHandler = new DropHandler(dropField.getElement());
		dropHandler.addFileEventHandler(new FileEventHandler() {		
			@Override
			public void onFiles(FileEvent event) {
				for(final File f: event.getFiles()) { 
//					if(f.getType().equals("image/png")) {
						new FileBlobReader(f, new AsyncCallback<String>() {
							
							@Override
							public void onSuccess(String result) {
								System.out.println("Size: " + result.length());
								dispatcher.execute(new ReplicateFileDataAction(f.getFileName(), result), 
										new AsyncCallback<StringResult>() {

									@Override
									public void onFailure(Throwable caught) {}

									@Override
									public void onSuccess(StringResult result) {
										System.out.println(result.get());
									}
								});
							}
							
							@Override
							public void onFailure(Throwable caught) {}
						});
						return;

//					}
				}
			}
		});
		
	}

}
