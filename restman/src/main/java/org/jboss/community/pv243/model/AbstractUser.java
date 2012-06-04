package org.jboss.community.pv243.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@MappedSuperclass
public abstract class AbstractUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Email
	@Column(name="email",unique=true)
	private String email;
	
	@NotNull
	@Column(name="password")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
