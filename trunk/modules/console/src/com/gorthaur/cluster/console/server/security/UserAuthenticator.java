package com.gorthaur.cluster.console.server.security;

public interface UserAuthenticator {

	boolean isValid(String username, String password);
	
}
