package com.epam.DocuSignDemo.jwt;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

public class DocuSignRestClientFactory {

	public static DocuSignRestClient getDocuSignRestClient() {
		ClientConfig config = new ClientConfig();
		config.connectorProvider(new ApacheConnectorProvider());
		
		Client restClient = ClientBuilder.newClient(config);
		restClient.property(ClientProperties.CONNECT_TIMEOUT, 5*1000);
		restClient.property(ClientProperties.READ_TIMEOUT, 60*10*1000);
		
		restClient.register(new ClientRequestFilter() {
			
			@Override
			public void filter(ClientRequestContext requestContext) throws IOException {
				requestContext.getHeaders().add("Authorization", "Bearer " + AccessTokenGenerator.getAccessToken());
			}
		});
		
		WebTarget target = restClient.target("https://account-d.docusign.com");
		DocuSignRestClient docuSignRestClient = WebResourceFactory.newResource(DocuSignRestClient.class, target);
		return docuSignRestClient;
	}
}
