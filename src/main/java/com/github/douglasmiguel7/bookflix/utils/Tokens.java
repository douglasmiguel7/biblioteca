package com.github.douglasmiguel7.bookflix.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.douglasmiguel7.bookflix.hardcode.SecurityConstant;
import com.github.douglasmiguel7.bookflix.output.TokenOutput;
import com.github.douglasmiguel7.bookflix.security.CurrentUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class Tokens {

	private Tokens() {

	}

	public static void addAuthentication(HttpServletResponse response, Authentication authentication) {
		CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();

		String token = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, SecurityConstant.TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.TOKEN_EXPIRATION_TIME))
				.setIssuedAt(new Date())
				.setIssuer(SecurityConstant.APP_URL)
				.setSubject(currentUser.getUsername())
				.claim("user", currentUser)
				.compact();

		token = SecurityConstant.TOKEN_PREFIX + token;

		try {
			String tokenOutput = new ObjectMapper().writeValueAsString(new TokenOutput(token));

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print(tokenOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Authentication getByToken(String token) {
		String user = Jwts.parser()
				.setSigningKey(SecurityConstant.TOKEN_KEY)
				.parseClaimsJws(token.replace(SecurityConstant.TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();

		return user != null ? new UsernamePasswordAuthenticationToken(user, null, null) : null;
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstant.TOKEN_HEADER_NAME);

		if (token != null) {
			return getByToken(token);
		}

		return null;
	}

}
