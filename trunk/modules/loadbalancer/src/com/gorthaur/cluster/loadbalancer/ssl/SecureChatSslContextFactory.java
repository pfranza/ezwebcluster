package com.gorthaur.cluster.loadbalancer.ssl;

import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class SecureChatSslContextFactory {

	private static final String PROTOCOL = "TLS";
	private static final SSLContext SERVER_CONTEXT;
	private static final SSLContext CLIENT_CONTEXT;

	static {
		String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = "SunX509";
		}

		SSLContext serverContext = null;
		SSLContext clientContext = null;
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(SecureChatKeyStore.asInputStream(),
					SecureChatKeyStore.getKeyStorePassword());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
			kmf.init(ks, SecureChatKeyStore.getCertificatePassword());

			// Initialize the SSLContext to work with our key managers.
			serverContext = SSLContext.getInstance(SecureChatSslContextFactory.PROTOCOL);
			serverContext.init(kmf.getKeyManagers(), null, null);
		} catch (Exception e) {
			throw new Error(
					"Failed to initialize the server-side SSLContext", e);
		}

		try {
			clientContext = SSLContext.getInstance(SecureChatSslContextFactory.PROTOCOL);
			clientContext.init(null, SecureChatTrustManagerFactory.getTrustManagers(), null);
		} catch (Exception e) {
			throw new Error(
					"Failed to initialize the client-side SSLContext", e);
		}

		SERVER_CONTEXT = serverContext;
		CLIENT_CONTEXT = clientContext;
	}

	public static SSLContext getServerContext() {
		return SecureChatSslContextFactory.SERVER_CONTEXT;
	}

	public static SSLContext getClientContext() {
		return SecureChatSslContextFactory.CLIENT_CONTEXT;
	}
}
