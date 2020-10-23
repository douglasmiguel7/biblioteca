package com.github.douglasmiguel7.bookflix.livro.utils;

import com.github.douglasmiguel7.bookflix.livro.domain.Livro;
import com.github.douglasmiguel7.bookflix.usuario.domain.Usuario;
import org.springframework.stereotype.Component;

@Component("livros")
public class Livros {

	public static boolean isAlugado(Livro livro) {
		return livro != null && livro.getUsuarioAluguel() != null && livro.getUsuarioAluguel().getId() != null;
	}

	public static boolean isAlugadoPorUsuario(Livro livro, Usuario usuario) {
		return livro != null && livro.getUsuarioAluguel() != null && livro.getUsuarioAluguel().equals(usuario);
	}

	public boolean alugado(Livro livro) {
		return Livros.isAlugado(livro);
	}

	public boolean alugadoPorUsuario(Livro livro, Usuario usuario) {
		return Livros.isAlugadoPorUsuario(livro, usuario);
	}

}
