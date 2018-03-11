package br.com.douglas.biblioteca.domain;

import br.com.douglas.biblioteca.input.LivroInput;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "LIVRO")
public class Livro {

    @Id
    @GenericGenerator(name = "livroSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "LIVRO_SEQUENCE"), @Parameter(name = "initial_value", value = "6")})
    @GeneratedValue(generator = "livroSequenceGenerator")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Livro{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
