package com.github.douglasmiguel7.bookflix.configuration;

import com.github.douglasmiguel7.bookflix.filter.JWTAuthorizationFilter;
import com.github.douglasmiguel7.bookflix.filter.JWTLoginFilter;
import com.github.douglasmiguel7.bookflix.hardcode.SecurityConstant;
import com.github.douglasmiguel7.bookflix.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public SecurityConfiguration(AuthService usuarioService) {
		this.userDetailsService = usuarioService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(SecurityConstant.LOGIN_URL, authenticationManager());
		JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager(), userDetailsService);

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);

		http
				.cors()
				.configurationSource(request -> corsConfiguration)
				.and()
				.csrf()
				.disable()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstant.LOGIN_URL).permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterAfter(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilter(jwtAuthorizationFilter)
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

}
