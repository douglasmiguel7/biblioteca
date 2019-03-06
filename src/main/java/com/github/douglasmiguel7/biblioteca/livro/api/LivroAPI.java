package com.github.douglasmiguel7.biblioteca.livro.api;

import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import com.github.douglasmiguel7.biblioteca.livro.input.LivroInput;
import com.github.douglasmiguel7.biblioteca.livro.input.wrapper.LivroInputWrapper;
import com.github.douglasmiguel7.biblioteca.livro.output.LivroOutput;
import com.github.douglasmiguel7.biblioteca.livro.output.wrapper.LivroOutputWrapper;
import com.github.douglasmiguel7.biblioteca.livro.output.wrapper.LivrosOutputWrapper;
import com.github.douglasmiguel7.biblioteca.livro.repository.LivroRepository;
import com.github.douglasmiguel7.biblioteca.shared.api.ResourceV1API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LivroAPI extends ResourceV1API {

	private LivroRepository livroRepository;

	@Autowired
	public LivroAPI(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	@PostMapping("/books")
	public LivroOutputWrapper create(@RequestBody LivroInputWrapper livroInputWrapper) {
		LivroInput livroInput = livroInputWrapper.getLivroInput();

		Livro livro = new Livro();
		livro.setNome(livroInput.getNome());

		livro = livroRepository.save(livro);

		LivroOutput livroOutput = new LivroOutput();
		livroOutput.setId(livro.getId());
		livroOutput.setNome(livro.getNome());

		LivroOutputWrapper livroOutputWrapper = new LivroOutputWrapper();
		livroOutputWrapper.setLivroOutput(livroOutput);

		return livroOutputWrapper;
	}

	@GetMapping("/books")
	public LivrosOutputWrapper read() {
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

	@PutMapping("/books/{id}")
	public ResponseEntity update(@PathVariable Long id, @RequestBody LivroInputWrapper livroInputWrapper) {
		Optional<Livro> livroOptional = livroRepository.findById(id);

		if (!livroOptional.isPresent()) {
			return new ResponseEntity("Livro com ID " + id + " não existe.", HttpStatus.BAD_REQUEST);
		}

		LivroInput livroInput = livroInputWrapper.getLivroInput();
		String nome = livroInput.getNome();

		Livro livro = livroOptional.get();
		livro.setNome(nome);

		livroRepository.save(livro);

		Long livroId = livro.getId();
		String livroNome = livro.getNome();

		LivroOutput livroOutput = new LivroOutput();
		livroOutput.setId(livroId);
		livroOutput.setNome(livroNome);

		LivroOutputWrapper livroOutputWrapper = new LivroOutputWrapper();
		livroOutputWrapper.setLivroOutput(livroOutput);

		return new ResponseEntity(livroOutputWrapper, HttpStatus.OK);
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		Optional<Livro> livroOptional = livroRepository.findById(id);

		if (!livroOptional.isPresent()) {
			return new ResponseEntity("Livro com ID " + id + " não existe.", HttpStatus.BAD_REQUEST);
		}

		Livro livro = livroOptional.get();

		livroRepository.delete(livro);

		return new ResponseEntity("Livro excluído.", HttpStatus.OK);
	}

}
