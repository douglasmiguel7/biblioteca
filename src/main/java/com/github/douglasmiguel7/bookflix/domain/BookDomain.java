package com.github.douglasmiguel7.bookflix.domain;

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
@Table(name = "book")
public class BookDomain {

	@Id
	@GenericGenerator(name = "livroSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "book_sequence")})
	@GeneratedValue(generator = "livroSequenceGenerator")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

    @ManyToOne
    @JoinColumn(name = "user_lessee_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "book_user_lessee_id_fk"))
	private UserDomain userLessee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public UserDomain getUserLessee() {
        return userLessee;
    }

    public void setUserLessee(UserDomain userLessee) {
        this.userLessee = userLessee;
    }

}
