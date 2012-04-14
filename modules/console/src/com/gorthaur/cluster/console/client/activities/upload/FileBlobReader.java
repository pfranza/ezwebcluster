package com.gorthaur.cluster.console.client.activities.upload;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class FileBlobReader {

	private String fileData;
	private AsyncCallback<String> callback;
	
	public FileBlobReader(File file, AsyncCallback<String> callback) {
		System.out.println("Create Blob Reader");
		this.callback = callback;
		_getAsDataURL(file);
	}
	
	protected void filesDropped() {
        callback.onSuccess(fileData);
    }
	
	private final native void _getAsDataURL(File object) /*-{
		var _this = this;
		reader = new FileReader();
		reader.onload = function(event) {		
			_this.@com.gorthaur.cluster.console.client.activities.upload.FileBlobReader::fileData = event.target.result;
			_this.@com.gorthaur.cluster.console.client.activities.upload.FileBlobReader::filesDropped()();
		};
		reader.readAsDataURL(object);
	}-*/;
	
}
