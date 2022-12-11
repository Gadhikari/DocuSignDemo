package com.epam.DocuSignDemo.jwt;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Consumes("{application/json}")
@Produces("{application/json}")
public interface DocuSignRestClient {
	
	@GET
	@Path("/oauth/userinfo")
	Response getUserInfo();

	@POST
	@Path("/oauth/token")
	Response getAccessToken(HashMap<String, String> requestBody);
}
