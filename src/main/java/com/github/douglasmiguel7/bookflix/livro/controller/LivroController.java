package com.github.douglasmiguel7.bookflix.livro.controller;

import com.github.douglasmiguel7.bookflix.auth.service.AuthService;
import com.github.douglasmiguel7.bookflix.livro.domain.Livro;
import com.github.douglasmiguel7.bookflix.livro.exception.LivroIndisponivelException;
import com.github.douglasmiguel7.bookflix.livro.exception.LivroNaoAlugadoException;
import com.github.douglasmiguel7.bookflix.livro.repository.LivroRepository;
import com.github.douglasmiguel7.bookflix.livro.service.LivroService;
import com.github.douglasmiguel7.bookflix.shared.controller.WebAppController;
import com.github.douglasmiguel7.bookflix.usuario.domain.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class LivroController extends WebAppController {

	private LivroRepository livroRepository;

	private LivroService livroService;

	private AuthService authService;

	@Autowired
	public LivroController(LivroRepository livroRepository, LivroService livroService, AuthService authService) {
		this.livroRepository = livroRepository;
		this.livroService = livroService;
		this.authService = authService;
	}

	@RequestMapping({"", "/", "/home"})
	@PreAuthorize("hasAnyRole('ROLE_BOOK_CREATE', 'ROLE_BOOK_EDIT', 'ROLE_BOOK_SEARCH', 'ROLE_BOOK_DELETE', 'ROLE_BOOK_READ', 'ROLE_BOOK_RENT')")	public String home(Model model) {
		List<Livro> livros = livroRepository.findAll();

		model.addAttribute("livros", livros);

		return "livro/livros";
	}

	@PreAuthorize("hasRole('ROLE_BOOK_CREATE')")
	@PostMapping(value = "/book/create", params = "nome")
	public String create(@RequestParam("nome") String nome) {
		Livro livro = new Livro();
		livro.setNome(nome);

		livroRepository.save(livro);

		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_BOOK_SEARCH')")
	@GetMapping(value = "/book/search", params = "id")
	public String read(Model model, @RequestParam("id") String id) {
		if (!StringUtils.isNumeric(id)) {
			return "livro/livros";
		}

		Long livroId = Long.valueOf(id);

		Optional<Livro> optionalLivro = livroRepository.findById(livroId);

		if (!optionalLivro.isPresent()) {
			return "livro/livros";
		}

		Livro livro = optionalLivro.get();

		List<Livro> livros = new ArrayList<>();
		livros.add(livro);

		model.addAttribute("livros", livros);

		return "livro/livros";
	}

	@PreAuthorize("hasRole('ROLE_BOOK_EDIT')")
	@PostMapping(value = "/book/update", params = {"id", "nome"})
	public String update(@RequestParam("id") String id, @RequestParam("nome") String nome) {
		if (!StringUtils.isNumeric(id)) {
			return "redirect:/";
		}

		Long livroId = Long.valueOf(id);

		Optional<Livro> optionalLivro = livroRepository.findById(livroId);

		if (!optionalLivro.isPresent()) {
			return "redirect:/";
		}

		Livro livro = optionalLivro.get();
		livro.setNome(nome);

		livroRepository.save(livro);

		return "redirect:/";
	}

	@PreAuthorize("hasAnyRole('ROLE_BOOK_DELETE')")
	@PostMapping(value = "/book/delete", params = {"id"})
	public String delete(@RequestParam("id") String id) {
		if (!StringUtils.isNumeric(id)) {
			return "redirect:/";
		}

		Long livroId = Long.valueOf(id);

		Optional<Livro> optionalLivro = livroRepository.findById(livroId);

		if (!optionalLivro.isPresent()) {
			return "redirect:/";
		}

		Livro livro = optionalLivro.get();

		livroRepository.delete(livro);

		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_BOOK_RENT')")
	@PostMapping(value = "/book/rent", params = "id")
	public String rent(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		if (!StringUtils.isNumeric(id)) {
			return "redirect:/";
		}

		Long livroId = Long.valueOf(id);

		Optional<Livro> optionalLivro = livroRepository.findById(livroId);

		if (!optionalLivro.isPresent()) {
			return "redirect:/";
		}

		Livro livro = optionalLivro.get();

		Usuario usuario = authService.getUsuarioLogado();

		try {
			livroService.alugar(livro, usuario);
		} catch (LivroIndisponivelException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", StringUtils.join("Livro \"", livro.getNome(), "\" indisponível"));
		}

		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_BOOK_GIVEBACK')")
	@PostMapping(value = "/book/give-back", params = "id")
	public String giveBack(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		if (!StringUtils.isNumeric(id)) {
			return "redirect:/";
		}

		Long livroId = Long.valueOf(id);

		Optional<Livro> optionalLivro = livroRepository.findById(livroId);

		if (!optionalLivro.isPresent()) {
			return "redirect:/";
		}

		Livro livro = optionalLivro.get();

		Usuario usuario = authService.getUsuarioLogado();

		try {
			livroService.devolver(livro, usuario);
		} catch (LivroNaoAlugadoException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", StringUtils.join("Livro \"", livro.getNome(), "\" indisponível"));
		}

		return "redirect:/";
	}

}
