package com.github.douglasmiguel7.biblioteca.livro.input.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.douglasmiguel7.biblioteca.livro.input.LivroInput;

public class LivroInputWrapper {

    @JsonProperty("livro")
    private LivroInput livroInput;

    public LivroInput getLivroInput() {
        return livroInput;
    }

    public void setLivroInput(LivroInput livroInput) {
        this.livroInput = livroInput;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LivroInputWrapper{");
        sb.append("livroInput=").append(livroInput);
        sb.append('}');
        return sb.toString();
    }
}
