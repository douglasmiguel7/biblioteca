package com.github.douglasmiguel7.bookflix.hardcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstant {

	public static String TOKEN_KEY;
	public static String APP_URL;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER_NAME = "Authorization";
	public static final long TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7 days

	public static final String LOGIN_URL = "/api/v1/login";

	public SecurityConstant(@Value("${app.token.key}") String tokenKey, @Value("${app.url}") String appUrl) {
		SecurityConstant.TOKEN_KEY = tokenKey;
		SecurityConstant.APP_URL = appUrl;
	}

}
