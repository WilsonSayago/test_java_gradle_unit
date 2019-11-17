package com.base.app.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "users")
@Entity
public class User extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@NotNull(message = "Name can not be empty")
	private String name;
	
	//Validacion con dominio .cl: ^[^@]+@[^@]+\.cl$
	//Validacion con cualquier dominio: ^[^@]+@[^@]+\\.[a-zA-Z]{2,}$
	@Email(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Email do not have the format accepted")
	@Column(nullable = false, unique = true)
	@NotNull(message = "Email can not be empty")
	private String email;
	
	@Column(nullable = false)
	@NotNull(message = "Password can not be empty")
	private String password;
	
	@Column(name = "last_login")
	@JsonProperty("last_login")
	private Date lastLogin;
	
	private String token;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	List<Phone> phones = new ArrayList<>();
	
	
	
	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date modified) {
		this.lastLogin = modified;
	}	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@PrePersist
	private void prePersistUser() {
		this.lastLogin = created;
	}
}
