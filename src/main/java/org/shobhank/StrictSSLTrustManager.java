/**
 * 
 */
package org.shobhank;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author shsharma
 *
 */
public class StrictSSLTrustManager implements X509TrustManager {

	private static String _PREFIX = "["
			+ StrictSSLTrustManager.class.getSimpleName() + "]";
	private X509TrustManager x509Tm;

	/**
	 * 
	 */
	public StrictSSLTrustManager() {
		TrustManagerFactory tmf = null;
		try {
			// ssl.TrustManagerFactory.algorithm or pkix
			tmf = TrustManagerFactory.getInstance(TrustManagerFactory
					.getDefaultAlgorithm());
			// default cacerts from java
			tmf.init((KeyStore) null);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(_PREFIX + " " + e.getMessage());
		} catch (KeyStoreException e) {
			System.out.println(_PREFIX + " " + e.getMessage());
		}

		// Get hold of the default trust manager
		for (TrustManager tm : tmf.getTrustManagers()) {
			if (tm instanceof X509TrustManager) {
				x509Tm = (X509TrustManager) tm;
				break;
			}
		}
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		if (x509Tm == null)
			throw new CertificateException("Cacerts could not be loaded");

		System.out.println(_PREFIX + " Server Cert Issuer");
		for (X509Certificate cert : chain) {
			System.out.println(_PREFIX + " " + cert.getIssuerDN().getName()
					+ " " + cert.getSubjectDN().getName());
		}
		System.out.println(_PREFIX + " Client Trusted Issuers");
		X509Certificate trusts[] = x509Tm.getAcceptedIssuers();
		for (X509Certificate cert : trusts) {
			System.out.println(_PREFIX + " " + cert.getIssuerDN().getName());
		}

		x509Tm.checkServerTrusted(chain, authType);

		System.out.println(_PREFIX + " Certificate Validated");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return x509Tm.getAcceptedIssuers();
	}
}
