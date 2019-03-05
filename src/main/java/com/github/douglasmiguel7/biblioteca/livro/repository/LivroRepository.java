package com.github.douglasmiguel7.biblioteca.livro.repository;

import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
