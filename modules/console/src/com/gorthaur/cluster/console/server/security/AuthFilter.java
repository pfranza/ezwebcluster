package com.gorthaur.cluster.console.server.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

@Singleton
public class AuthFilter implements javax.servlet.Filter{

	private SecureSessionValidator validator;

	@Inject
	AuthFilter(SecureSessionValidator validator) {
		this.validator = validator;
	}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		
		if(validator.isValid("", req)) {
			arg2.doFilter(arg0, arg1);
		} else {
			resp.setStatus(401);
			resp.setHeader("WWW-Authenticate", "Basic realm=\"Console\"");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
