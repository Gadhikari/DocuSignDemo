package com.epam.DocuSignDemo.jwt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.epam.DocuSignDemo.models.JWTResponse;

public class AccessTokenGenerator {
	private static String jwtToken = null;
	private static String accessToken = null;
	private static Long lastUpdatedTime = null;
	private static Long maxTimeDiff = (long) (1*60);
	
	public static String getAccessToken() {
		Long currentTime = System.currentTimeMillis()/1000;
		if (Objects.isNull(jwtToken) || (currentTime-lastUpdatedTime) >= maxTimeDiff) {
			verifyAndGetAccessToken();
		}
		System.out.println("Access Token is: " + accessToken);
		return accessToken;
	}

	private static void verifyAndGetAccessToken() {
		Client restClient = ClientBuilder.newClient();
		restClient.property(ClientProperties.CONNECT_TIMEOUT, 5*1000);
		restClient.property(ClientProperties.READ_TIMEOUT, 60*10*1000);

		try {
			jwtToken = JWTBuilder.returnJWTToken();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String jwtTokenCopy = new String(jwtToken);
		/*restClient.register(new ClientRequestFilter() {

			@Override
			public void filter(ClientRequestContext requestContext) throws IOException {
				requestContext.getHeaders().add("Authorization", "Bearer " + jwtTokenCopy);
			}
		});*/

		WebTarget target = restClient.target("https://account-d.docusign.com");
		target = target.path("/oauth/token");

		HashMap<String, String> requestBody = new HashMap<>();
		requestBody.put("assertion", jwtToken);
		requestBody.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");

		Response response = target.request().post(Entity.json(requestBody));
		System.out.println(response);

		JWTResponse jwtResponse = response.readEntity(JWTResponse.class);
		accessToken = jwtResponse.getAccess_token();
		System.out.println("Access token expire in"+ jwtResponse.getExpires_in());
		lastUpdatedTime = System.currentTimeMillis()/1000;
	}
}
