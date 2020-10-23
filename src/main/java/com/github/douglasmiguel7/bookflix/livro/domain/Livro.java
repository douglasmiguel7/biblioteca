package com.github.douglasmiguel7.bookflix.livro.domain;

import com.github.douglasmiguel7.bookflix.usuario.domain.Usuario;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToOne
    @JoinColumn(name = "USUARIO_ALUGUEL_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "LIVRO_USUARIO_ALUGUEL_FK"))
	private Usuario usuarioAluguel;

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

    public Usuario getUsuarioAluguel() {
        return usuarioAluguel;
    }

    public void setUsuarioAluguel(Usuario usuarioAluguel) {
        this.usuarioAluguel = usuarioAluguel;
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
