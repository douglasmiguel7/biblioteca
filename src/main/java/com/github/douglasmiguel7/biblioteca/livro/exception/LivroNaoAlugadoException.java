package com.github.douglasmiguel7.biblioteca.livro.exception;

public class LivroNaoAlugadoException extends Exception {
	public LivroNaoAlugadoException() {
		super("Livro ainda não alugado");
	}
}
