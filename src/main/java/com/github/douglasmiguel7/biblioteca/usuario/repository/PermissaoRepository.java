package com.github.douglasmiguel7.biblioteca.usuario.repository;

import com.github.douglasmiguel7.biblioteca.usuario.domain.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

	List<Permissao> findByUsuarioPermissaos_usuario_username(String username);

}
