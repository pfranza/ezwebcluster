package com.gorthaur.cluster.console.server.security;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.gorthaur.cluster.console.server.util.Base64;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

public class DefaultSecureSessionValidator implements SecureSessionValidator {

	private UserAuthenticator auth;

	@Inject
	DefaultSecureSessionValidator(UserAuthenticator auth) {
		this.auth = auth;
	}
	
	@Override
	public boolean isValid(String sessionId, HttpServletRequest req) {

		String header = req.getHeader("Authorization");
		if(header == null || !header.substring(0, 6).equals("Basic ")) {
			return false;
		}
		String basicAuthEncoded = header.substring(6);
		String basicAuthAsString = new String(Base64.decode(basicAuthEncoded));
		String[] authParts = basicAuthAsString.split(":");
		return authParts.length == 2 ? auth.isValid(authParts[0], authParts[1]) : false;
	}

}
