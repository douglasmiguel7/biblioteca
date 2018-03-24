package br.com.douglas.biblioteca.output.wrapper;

import br.com.douglas.biblioteca.output.LivroOutput;
import com.fasterxml.jackson.annotation.JsonProperty;

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
