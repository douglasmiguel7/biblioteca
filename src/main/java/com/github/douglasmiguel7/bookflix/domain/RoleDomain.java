package com.github.douglasmiguel7.bookflix.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "role")
public class RoleDomain {

	@Id
	@GenericGenerator(name = "roleSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "role_sequence"), @Parameter(name = "initial_value", value = "8")})
	@GeneratedValue(generator = "roleSequenceGenerator")
	@Column(name = "id")
	private Long id;

	@Column(name = "family")
	private String family;

	@Column(name = "permission")
	private String permission;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name="role_id")}, inverseJoinColumns = {@JoinColumn(name="user_id")})
	private Set<UserDomain> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Set<UserDomain> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDomain> userDomains) {
		this.users = userDomains;
	}

}
