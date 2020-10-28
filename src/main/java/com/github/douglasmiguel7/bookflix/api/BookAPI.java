package com.github.douglasmiguel7.bookflix.api;

import com.github.douglasmiguel7.bookflix.domain.BookDomain;
import com.github.douglasmiguel7.bookflix.domain.UserDomain;
import com.github.douglasmiguel7.bookflix.input.BookInput;
import com.github.douglasmiguel7.bookflix.output.BookOutput;
import com.github.douglasmiguel7.bookflix.repository.BookRepository;
import com.github.douglasmiguel7.bookflix.repository.UserRepository;
import com.github.douglasmiguel7.bookflix.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookAPI extends BaseAPI {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/books")
	public ResponseEntity createBook(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody @Valid BookInput bookInput, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		UserDomain userDomain = userRepository.findByUsername(currentUser.getUsername());

		BookDomain bookDomain = new BookDomain();
		bookDomain.setName(bookInput.getName());
		bookDomain.setUserLessee(userDomain);

		bookRepository.save(bookDomain);

		BookOutput bookOutput = new BookOutput();
		bookOutput.setId(bookDomain.getId());
		bookOutput.setName(bookDomain.getName());

		URI location = uriBuilder.path("/api/v1/books/{id}").buildAndExpand(bookDomain.getId()).toUri();

		return ResponseEntity.created(location).body(bookOutput);
	}

	@GetMapping("/books")
	public ResponseEntity getBooks() {
		List<BookOutput> bookOutputs = new ArrayList<>();

		List<BookDomain> bookDomains = bookRepository.findAll();

		for (BookDomain bookDomain : bookDomains) {
			BookOutput bookOutput = new BookOutput();
			bookOutput.setId(bookDomain.getId());
			bookOutput.setName(bookDomain.getName());

			bookOutputs.add(bookOutput);
		}

		return ResponseEntity.ok(bookOutputs);
	}

	@PutMapping("/books/{id}")
	public ResponseEntity updateBook(@PathVariable Long id, @RequestBody @Valid BookInput bookInput, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		BookDomain bookDomain = bookRepository.getOne(id);

		if (bookDomain == null) {
			return ResponseEntity.notFound().build();
		}

		bookDomain.setName(bookInput.getName());

		bookRepository.save(bookDomain);

		BookOutput bookOutput = new BookOutput();
		bookOutput.setId(bookDomain.getId());
		bookOutput.setName(bookDomain.getName());

		return ResponseEntity.ok(bookOutput);
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity deleteBook(@PathVariable Long id) {
		try {
			bookRepository.deleteById(id);
		} catch (Exception e) {

		}

		return ResponseEntity.ok().build();
	}

}
