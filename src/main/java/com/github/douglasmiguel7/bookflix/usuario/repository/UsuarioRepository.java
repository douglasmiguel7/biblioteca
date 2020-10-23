package com.github.douglasmiguel7.bookflix.usuario.repository;

import com.github.douglasmiguel7.bookflix.auth.projection.UsernamePasswordProjection;
import com.github.douglasmiguel7.bookflix.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UsernamePasswordProjection findUsernamePasswordByUsername(String username);

	Usuario findByUsername(String username);

}
