package com.gorthaur.cluster.console.client.activities.upload;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasFileEvents {
    HandlerRegistration addFileEventHandler(FileEvent.FileEventHandler handler);
}