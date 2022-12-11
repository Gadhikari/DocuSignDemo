package com.epam.DocuSignDemo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.ws.rs.core.Response;

import com.epam.DocuSignDemo.jwt.DocuSignRestClient;
import com.epam.DocuSignDemo.jwt.DocuSignRestClientFactory;
import com.epam.DocuSignDemo.models.UserInfo;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		DocuSignRestClient docuSignRestClient = DocuSignRestClientFactory.getDocuSignRestClient();
		Scanner in = new Scanner(System.in);
		
		System.out.println("Input 1 for request: ");
		
		while(Integer.valueOf(in.nextLine()) == 1) {
			System.out.println("Input 1 for request: ");
			Response response = docuSignRestClient.getUserInfo();
			System.out.println(response);
			System.out.println(response.readEntity(UserInfo.class));
		}
	}
}
