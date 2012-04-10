package com.gorthaur.cluster.console.client;

import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

public class DefaultSecureSessionAccessor extends CookieSecureSessionAccessor implements SecureSessionAccessor {

	public DefaultSecureSessionAccessor() {
		super("GameEditor");
	}

}
