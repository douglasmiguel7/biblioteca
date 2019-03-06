package com.github.douglasmiguel7.biblioteca.auth.security;

import com.github.douglasmiguel7.biblioteca.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public WebSecurity(AuthService usuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = usuarioService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/app/login", "/app/logar", "/api/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/app/logar")
				.loginPage("/app/login")
				.usernameParameter("usuario")
				.passwordParameter("senha")
				.defaultSuccessUrl("/app/home")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/app/logout")
				.logoutSuccessUrl("/app/login")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
