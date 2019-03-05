package com.github.douglasmiguel7.biblioteca.livro.output.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.douglasmiguel7.biblioteca.livro.output.LivroOutput;

import java.util.ArrayList;
import java.util.List;

public class LivrosOutputWrapper {

    @JsonProperty("livros")
    private List<LivroOutput> livroOutputs = new ArrayList<>();

    public List<LivroOutput> getLivroOutputs() {
        return livroOutputs;
    }

    public void setLivroOutputs(List<LivroOutput> livroOutputs) {
        this.livroOutputs = livroOutputs;
    }
}
