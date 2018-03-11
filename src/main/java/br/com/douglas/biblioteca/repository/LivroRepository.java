package br.com.douglas.biblioteca.repository;

import br.com.douglas.biblioteca.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
