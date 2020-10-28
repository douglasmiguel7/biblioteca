package com.github.douglasmiguel7.bookflix.domain;

import com.github.douglasmiguel7.bookflix.projection.UsernamePasswordRolesProjection;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDomain implements UsernamePasswordRolesProjection {

	@Id
	@GenericGenerator(name = "userSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "user_sequence"), @Parameter(name = "initial_value", value = "4")})
	@GeneratedValue(generator = "userSequenceGenerator")
	@Column(name = "ID")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "userLessee", fetch = FetchType.LAZY)
	private Set<BookDomain> rentedBooks;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name="role_id")})
	private Set<RoleDomain> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<BookDomain> getRentedBooks() {
		return rentedBooks;
	}

	public void setRentedBooks(Set<BookDomain> rentedBooks) {
		this.rentedBooks = rentedBooks;
	}

	public Set<RoleDomain> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDomain> roleDomains) {
		this.roles = roleDomains;
	}

}