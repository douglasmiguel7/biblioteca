package com.github.douglasmiguel7.bookflix.auth.service;

import com.github.douglasmiguel7.bookflix.auth.projection.UsernamePasswordProjection;
import com.github.douglasmiguel7.bookflix.usuario.domain.Permissao;
import com.github.douglasmiguel7.bookflix.usuario.domain.Usuario;
import com.github.douglasmiguel7.bookflix.usuario.repository.PermissaoRepository;
import com.github.douglasmiguel7.bookflix.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	private PermissaoRepository permissaoRepository;

	@Autowired
	public AuthService(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.permissaoRepository = permissaoRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UsernamePasswordProjection projection = usuarioRepository.findUsernamePasswordByUsername(s);

		if (projection == null) {
			throw new UsernameNotFoundException("Usuário ou senha incorretos");
		}

		String username = projection.getUsername();
		String password = projection.getPassword();

		List<Permissao> permissoes = permissaoRepository.findByUsuarioPermissaos_usuario_username(username);

		List<SimpleGrantedAuthority> simpleGrantedAuthorities = permissoes.stream().map(permissao -> new SimpleGrantedAuthority(permissao.getRole())).collect(Collectors.toList());

		User user = new User(username, password, simpleGrantedAuthorities);

		return user;
	}

	public Usuario getUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		String username = authentication.getName();

		return usuarioRepository.findByUsername(username);
	}
}
