package com.github.douglasmiguel7.biblioteca.usuario.domain;

import com.github.douglasmiguel7.biblioteca.auth.projection.UsernamePasswordProjection;
import com.github.douglasmiguel7.biblioteca.livro.domain.Livro;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
public class Usuario implements UsernamePasswordProjection {

	@Id
	@GenericGenerator(name = "usuarioSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "USUARIO_SEQUENCE")})
	@GeneratedValue(generator = "usuarioSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<UsuarioPermissao> usuarioPermissaos;

	@OneToMany(mappedBy = "usuarioAluguel", fetch = FetchType.LAZY)
	private Set<Livro> livroAlugados;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UsuarioPermissao> getUsuarioPermissaos() {
		return usuarioPermissaos;
	}

	public void setUsuarioPermissaos(Set<UsuarioPermissao> usuarioPermissaos) {
		this.usuarioPermissaos = usuarioPermissaos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return id.equals(usuario.id) &&
				username.equals(usuario.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}
}