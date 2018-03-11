package br.com.douglas.biblioteca.output.wrapper;

import br.com.douglas.biblioteca.domain.Livro;
import br.com.douglas.biblioteca.output.LivroOutput;
import com.fasterxml.jackson.annotation.JsonProperty;

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
