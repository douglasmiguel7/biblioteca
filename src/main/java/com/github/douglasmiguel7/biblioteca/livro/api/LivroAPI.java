package com.github.douglasmiguel7.biblioteca.livro.api;

import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import com.github.douglasmiguel7.biblioteca.livro.output.LivroOutput;
import com.github.douglasmiguel7.biblioteca.livro.output.wrapper.LivrosOutputWrapper;
import com.github.douglasmiguel7.biblioteca.livro.repository.LivroRepository;
import com.github.douglasmiguel7.biblioteca.shared.api.ResourceV1API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LivroAPI extends ResourceV1API {

	private LivroRepository livroRepository;

	@Autowired
	public LivroAPI(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	@GetMapping("/books")
	public LivrosOutputWrapper info() {
		List<LivroOutput> livroOutputs = new ArrayList<>();

		List<Livro> livros = livroRepository.findAll();

		for (Livro livro : livros) {
			Long id = livro.getId();
			String nome = livro.getNome();

			LivroOutput livroOutput = new LivroOutput();
			livroOutput.setId(id);
			livroOutput.setNome(nome);

			livroOutputs.add(livroOutput);
		}

		LivrosOutputWrapper livrosOutputWrapper = new LivrosOutputWrapper();
		livrosOutputWrapper.setLivroOutputs(livroOutputs);

		return livrosOutputWrapper;
	}

}
