package org.shobhank;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

/**
 *
 * @author shsharma
 */
public class CustomRestClient {
	
	public static HttpClient createCustomHttpClientStrict() {
		HttpClientBuilder builder = HttpClientBuilder.create();
		SSLContext sslContext = null;
		try {
			sslContext = SSLContextBuilder.create().build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TrustManager[] serverTMs = new TrustManager [] {new StrictSSLTrustManager()};
		
		try {
			sslContext.init(null, serverTMs, null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder.setSSLContext(sslContext);
		
		return builder.build();
	}
	
	public static HttpClient createCustomHttpClientLinient(){
		HttpClientBuilder builder = HttpClientBuilder.create();
		SSLContext sslContext = null;
		try {
			sslContext = SSLContextBuilder.create().build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TrustManager[] serverTMs = new TrustManager [] {new LinientSSLTrustManager()};
		
		try {
			sslContext.init(null, serverTMs, null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder.setSSLContext(sslContext);
		return builder.build();
	}
}
