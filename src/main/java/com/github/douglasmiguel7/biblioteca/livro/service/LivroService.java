package com.github.douglasmiguel7.biblioteca.livro.service;

import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import com.github.douglasmiguel7.biblioteca.livro.exception.LivroIndisponivelException;
import com.github.douglasmiguel7.biblioteca.livro.exception.LivroNaoAlugadoException;
import com.github.douglasmiguel7.biblioteca.livro.repository.LivroRepository;
import com.github.douglasmiguel7.biblioteca.livro.utils.Livros;
import com.github.douglasmiguel7.biblioteca.usuario.domain.Usuario;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

	private LivroRepository livroRepository;

	public LivroService(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	public void alugar(Livro livro, Usuario usuario) throws LivroIndisponivelException {
		if (Livros.isAlugado(livro)) {
			throw new LivroIndisponivelException();
		}

		livro.setUsuarioAluguel(usuario);

		livroRepository.save(livro);
	}

	public void devolver(Livro livro, Usuario usuario) throws LivroNaoAlugadoException {
		if (!Livros.isAlugado(livro)) {
			throw new LivroNaoAlugadoException();
		}

		livro.setUsuarioAluguel(null);

		livroRepository.save(livro);
	}
}
