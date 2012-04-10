package com.gorthaur.cluster.console.client;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.client.gin.AbstractDispatchModule;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;
import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

import com.google.inject.Provides;
import com.google.inject.Singleton;

public class SecureDispatchModule extends AbstractDispatchModule {

    /**
     * Constructs a new GIN configuration module that sets up a secure {@link net.customware.gwt.dispatch.client.DispatchAsync}
     * implementation, using the {@link net.customware.gwt.dispatch.client.DefaultExceptionHandler}.
     */
    public SecureDispatchModule() {
        this( DefaultExceptionHandler.class );
    }

    /**
     * Constructs a new GIN configuration module that sets up a secure {@link net.customware.gwt.dispatch.client.DispatchAsync}
     * implementation, using the provided {@link ExceptionHandler} implementation class.
     *
     * @param exceptionHandlerType The {@link ExceptionHandler} implementation class.
     */
    public SecureDispatchModule( Class<? extends ExceptionHandler> exceptionHandlerType ) {
        super( exceptionHandlerType );
    }
	
	@Provides
    @Singleton
    protected DispatchAsync provideDispatchAsync( ExceptionHandler exceptionHandler, SecureSessionAccessor secureSessionAccessor ) {
        return new SecureDispatchAsync( exceptionHandler, secureSessionAccessor );
    }
	
}
