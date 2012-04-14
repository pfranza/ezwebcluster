package com.gorthaur.cluster.console.client.activities.upload;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FileEvent extends GwtEvent<FileEvent.FileEventHandler> {

    public interface FileEventHandler extends EventHandler {

        void onFiles(FileEvent event);
    }

    public static final Type<FileEventHandler> TYPE = new GwtEvent.Type<FileEventHandler>();
    private final ArrayList<File> files = new ArrayList<File>();;

    public FileEvent(JsArray<File> files){
    	for (int i = 0; i < files.length(); ++i) {
            File file = files.get(i);
            this.files.add(file);
    	}
    }

    @Override
    public GwtEvent.Type<FileEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<File> getFiles() {
        return this.files;
    }

    @Override
    protected void dispatch(FileEventHandler handler) {
        handler.onFiles(this);
    }
}