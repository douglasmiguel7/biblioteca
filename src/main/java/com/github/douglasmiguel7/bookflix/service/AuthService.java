package com.github.douglasmiguel7.bookflix.service;

import com.github.douglasmiguel7.bookflix.domain.RoleDomain;
import com.github.douglasmiguel7.bookflix.projection.UsernamePasswordRolesProjection;
import com.github.douglasmiguel7.bookflix.repository.UserRepository;
import com.github.douglasmiguel7.bookflix.security.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UsernamePasswordRolesProjection projection = userRepository.findUsernamePasswordRolesByUsername(s);

		if (projection == null) {
			throw new UsernameNotFoundException("Usuário ou senha incorretos");
		}

		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

		for (RoleDomain role : projection.getRoles()) {
			String grantedAuthority = StringUtils.joinWith("_", "ROLE", role.getFamily(), role.getPermission());
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(grantedAuthority));
		}

		CurrentUser currentUser = new CurrentUser(projection.getUsername(), projection.getPassword(), simpleGrantedAuthorities);

		return currentUser;
	}

}
