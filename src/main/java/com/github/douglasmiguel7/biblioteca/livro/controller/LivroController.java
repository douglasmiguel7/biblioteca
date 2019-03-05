package com.github.douglasmiguel7.biblioteca.livro.controller;

import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import com.github.douglasmiguel7.biblioteca.livro.repository.LivroRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @RequestMapping
    public String home(Model model) {
        List<Livro> livros = livroRepository.findAll();

        model.addAttribute("livros", livros);

        return "livros";
    }

    @PostMapping(params = "nome")
    public String create(@RequestParam("nome") String nome) {
        System.out.println("LivroController.create");
        Livro livro = new Livro();
        livro.setNome(nome);

        livroRepository.save(livro);

        return "redirect:/";
    }

    @GetMapping(params = "id")
    public String read(Model model, @RequestParam("id") String id) {
        System.out.println("LivroController.read");
        if (!StringUtils.isNumeric(id)) {
            return "livros";
        }

        Long livroId = Long.valueOf(id);

        Optional<Livro> optionalLivro = livroRepository.findById(livroId);

        if (!optionalLivro.isPresent()) {
            return "livros";
        }

        Livro livro = optionalLivro.get();

        List<Livro> livros = new ArrayList<>();
        livros.add(livro);

        model.addAttribute("livros", livros);

        return "livros";
    }

    @PostMapping(params = {"id", "nome"})
    public String update(@RequestParam("id") String id, @RequestParam("nome") String nome) {
        System.out.println("LivroController.update");
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

    @PostMapping(value = "delete", params = {"id"})
    public String delete(@RequestParam("id") String id) {
        System.out.println("LivroController.delete");
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

}
