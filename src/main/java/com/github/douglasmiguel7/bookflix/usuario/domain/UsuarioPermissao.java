package com.github.douglasmiguel7.bookflix.usuario.domain;

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
@Table(name = "USUARIO_PERMISSAO")
public class UsuarioPermissao {

	@Id
	@GenericGenerator(name = "usuarioPermissaoSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "USUARIO_PERMISSAO_SEQUENCE")})
	@GeneratedValue(generator = "usuarioPermissaoSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "USUARIO_PERMISSAO_USUARIO_FK"))
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "PERMISSAO_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "USUARIO_PERMISSAO_PERMISSAO_FK"))
	private Permissao permissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}
}
