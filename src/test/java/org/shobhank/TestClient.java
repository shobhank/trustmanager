package org.shobhank;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author shsharma
 */

@Test
public class TestClient {
	
	HttpGet get = new HttpGet("https://www.google.com");
	
	@Test
	public void testLinientClient() throws ClientProtocolException, IOException{
		HttpClient client = CustomRestClient.createCustomHttpClientLinient();
		HttpResponse response = client.execute(get);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
	}
	
	@Test(expectedExceptions = SSLHandshakeException.class)
	public void testStrictClient() throws ClientProtocolException, IOException{
		HttpClient client = CustomRestClient.createCustomHttpClientStrict();
		HttpResponse response = client.execute(get);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
	}
}
