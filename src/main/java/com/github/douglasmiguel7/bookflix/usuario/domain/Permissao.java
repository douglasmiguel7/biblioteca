package com.github.douglasmiguel7.bookflix.usuario.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "PERMISSAO")
public class Permissao {

	@Id
	@GenericGenerator(name = "permissaoSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "PERMISSAO_SEQUENCE")})
	@GeneratedValue(generator = "permissaoSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ROLE")
	private String role;

	@OneToMany(mappedBy = "permissao", fetch = FetchType.LAZY)
	private Set<UsuarioPermissao> usuarioPermissaos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<UsuarioPermissao> getUsuarioPermissaos() {
		return usuarioPermissaos;
	}

	public void setUsuarioPermissaos(Set<UsuarioPermissao> usuarioPermissaos) {
		this.usuarioPermissaos = usuarioPermissaos;
	}
}
