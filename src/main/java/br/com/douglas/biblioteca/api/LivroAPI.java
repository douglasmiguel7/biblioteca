package br.com.douglas.biblioteca.api;

import br.com.douglas.biblioteca.domain.Livro;
import br.com.douglas.biblioteca.input.LivroInput;
import br.com.douglas.biblioteca.input.wrapper.LivroInputWrapper;
import br.com.douglas.biblioteca.output.LivroOutput;
import br.com.douglas.biblioteca.output.wrapper.LivroOutputWrapper;
import br.com.douglas.biblioteca.output.wrapper.LivrosOutputWrapper;
import br.com.douglas.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroAPI {

    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
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

    @GetMapping
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

    @PutMapping("{id}")
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

    @DeleteMapping("{id}")
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
