package com.gorthaur.cluster.console.client.activities.upload;

import com.google.gwt.core.client.JavaScriptObject;

public class File extends JavaScriptObject {

	protected File() {
	}

	public final native String getFileName() /*-{
		return this.fileName;
	}-*/;

	public final native int getFileSize() /*-{
		return this.fileSize;
	}-*/;

	public final native String getType() /*-{
		return this.type;
	}-*/;
}