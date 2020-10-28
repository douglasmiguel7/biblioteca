package com.github.douglasmiguel7.bookflix.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRoleDomain {

	@Id
	@GenericGenerator(name = "userRoleSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "user_role_sequence")})
	@GeneratedValue(generator = "userRoleSequenceGenerator")
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "user_role_user_id_fk"))
	private UserDomain user;

	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "user_role_role_id_fk"))
	private RoleDomain role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDomain getUser() {
		return user;
	}

	public void setUser(UserDomain user) {
		this.user = user;
	}

	public RoleDomain getRole() {
		return role;
	}

	public void setRole(RoleDomain role) {
		this.role = role;
	}

}