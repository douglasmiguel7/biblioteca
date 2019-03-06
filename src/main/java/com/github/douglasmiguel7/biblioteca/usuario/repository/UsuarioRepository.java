package com.github.douglasmiguel7.biblioteca.usuario.repository;

import com.github.douglasmiguel7.biblioteca.auth.projection.UsernamePasswordProjection;
import com.github.douglasmiguel7.biblioteca.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UsernamePasswordProjection findUsernamePasswordByUsername(String username);

	Usuario findByUsername(String username);

}
