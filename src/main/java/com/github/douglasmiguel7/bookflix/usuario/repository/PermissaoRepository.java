package com.github.douglasmiguel7.bookflix.usuario.repository;

import com.github.douglasmiguel7.bookflix.usuario.domain.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

	List<Permissao> findByUsuarioPermissaos_usuario_username(String username);

}
