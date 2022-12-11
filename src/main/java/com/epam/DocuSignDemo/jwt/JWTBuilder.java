package com.epam.DocuSignDemo.jwt;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

// https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception
// https://stackoverflow.com/questions/6559272/algid-parse-error-not-a-sequence
// https://www.viralpatel.net/java-create-validate-jwt-token/
// https://stackoverflow.com/questions/49330180/generating-a-jwt-using-an-existing-private-key-and-rs256-algorithm

// openssl pkcs8 -topk8 -nocrypt -in privatekeyOLD -out privatekeyNEW
public class JWTBuilder {
	private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

	private static String rsaPrivateKey = "-----BEGIN PRIVATE KEY-----\n" +
			"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\n" +
			"BT4tifuA2bsPql49BOrPqw==\n" +
			"-----END PRIVATE KEY-----\n";

	private static PrivateKey getPrivateKey() 
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	//	rsaPrivateKey = rsaPrivateKey.replaceAll("\n", "");
	//	rsaPrivateKey = rsaPrivateKey.replaceAll("\r", "");
	//	rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
	//	rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");
		PemReader pemReader = new PemReader(new StringReader(rsaPrivateKey));
		PemObject pemObject = pemReader.readPemObject();
		byte[] content  = pemObject.getContent();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
		KeyFactory kf = KeyFactory.getInstance("RSA"); 
		PrivateKey privKey = kf.generatePrivate(keySpec);
		System.out.println("@@@@@@@@@@"+new Date());
		return privKey;
	}

	private static String createJWTAndSign(String integrationKey, String userId, String aud, String scope)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Long iat = System.currentTimeMillis() / 1000;
		Long exp = iat + 300L;//expiration time of JWT
		System.out.println("Initialization time is : " + iat);

		Claims claims = Jwts.claims();
		claims.put("iss", integrationKey);
		claims.put("sub", userId);
		claims.put("aud", aud);
		claims.put("iat", iat);
		claims.put("exp", exp);
		claims.put("scope", scope);
		PrivateKey privKey = getPrivateKey();
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ","JWT").setClaims(claims).signWith(signatureAlgorithm, privKey);

		return builder.compact();
	}

	public static String returnJWTToken()
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		String integrationKey = "516404fc-3dae-453e-90ea-187fac88e3f4"; // iss
		String userId = "2fea8488-dfb4-4d5c-bd56-31eaa9cf7407"; // sub
		String aud = "account-d.docusign.com";
		String scope = "signature impersonation";
		String jwtToken = createJWTAndSign(integrationKey, userId, aud, scope);
		System.out.println("JWT Token generated is : " + jwtToken);
		return jwtToken;
	}
}
