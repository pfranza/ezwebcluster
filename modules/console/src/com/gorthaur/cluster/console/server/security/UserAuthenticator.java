package com.gorthaur.cluster.console.server.security;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultUserAuthenticator.class)
public interface UserAuthenticator {

	boolean isValid(String username, String password);
	
}
