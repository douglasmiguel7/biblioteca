package com.github.douglasmiguel7.bookflix.livro.output.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.douglasmiguel7.bookflix.livro.output.LivroOutput;

public class LivroOutputWrapper {

    @JsonProperty("livro")
    private LivroOutput livroOutput;

    public LivroOutput getLivroOutput() {
        return livroOutput;
    }

    public void setLivroOutput(LivroOutput livroOutput) {
        this.livroOutput = livroOutput;
    }
}