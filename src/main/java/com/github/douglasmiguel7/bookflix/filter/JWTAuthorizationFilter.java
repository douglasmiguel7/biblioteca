package com.github.douglasmiguel7.bookflix.filter;

import com.github.douglasmiguel7.bookflix.hardcode.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService) {
		super(authManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader(SecurityConstant.TOKEN_HEADER_NAME);

		if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		Authentication authentication = getAuthentication(request);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstant.TOKEN_HEADER_NAME);

		if (token == null) {
			return null;
		}

		token = token.replace(SecurityConstant.TOKEN_PREFIX, "");

		String subject = Jwts.parser()
				.setSigningKey(SecurityConstant.TOKEN_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

		if (subject != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

			return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		}

		return null;
	}

}
