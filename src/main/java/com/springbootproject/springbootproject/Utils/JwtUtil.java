package com.springbootproject.springbootproject.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.springbootproject.springbootproject.model.UserInfo;

@Component
public class JwtUtil {

	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	private static final String USER = "user";
	private static final String SECRET = "daycaidaynaychinhlachukycuabandungdelorangoaidaynhenguyhiemchetnguoidayhihihi";

	public String generateToken(UserInfo user) {
		String token = null;
		try {
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(USER, user);
			builder.expirationTime(generateExpirationDate());// Thời gian tồn tại của token
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			JWSSigner signer = new MACSigner(SECRET.getBytes());
			signedJWT.sign(signer);
			token = signedJWT.serialize();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return token;
	}

	public Date generateExpirationDate() {
//		return new Date(System.currentTimeMillis() + 864000000);
		return new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12));
	}

	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (ParseException | JOSEException e) {
			logger.error(e.getMessage());
		}
		return claims;
	}

	public UserInfo getUserFromToken(String token) {
		UserInfo user = new UserInfo();
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			if (claims != null && isTokenExpired(claims)) {
				JSONObject jsonObject = (JSONObject) claims.getClaim(USER);
				user = new ObjectMapper().readValue(jsonObject.toJSONString(), UserInfo.class);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user;
	}

	private Date getExpirationDateFromToken(JWTClaimsSet claims) {
		return claims != null ? claims.getExpirationTime() : new Date();
	}

	private boolean isTokenExpired(JWTClaimsSet claims) {
		return getExpirationDateFromToken(claims).after(new Date());
	}

}