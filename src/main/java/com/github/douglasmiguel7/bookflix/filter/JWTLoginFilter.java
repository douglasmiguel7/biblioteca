package com.github.douglasmiguel7.bookflix.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.douglasmiguel7.bookflix.input.LoginInput;
import com.github.douglasmiguel7.bookflix.utils.Tokens;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		LoginInput loginInput = new ObjectMapper().readValue(request.getInputStream(), LoginInput.class);

		if (StringUtils.isBlank(loginInput.getUsername()) || StringUtils.isBlank(loginInput.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("Usuário ou senha inválido");
		}

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword(), Collections.emptyList());

		Authentication authentication = getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		Tokens.addAuthentication(response, authentication);
	}

}
