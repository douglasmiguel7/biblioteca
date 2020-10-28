package com.github.douglasmiguel7.bookflix.repository;

import com.github.douglasmiguel7.bookflix.domain.BookDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookDomain, Long> {

}
