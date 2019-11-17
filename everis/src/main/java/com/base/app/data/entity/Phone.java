package com.base.app.data.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "phones")
public class Phone extends BaseEntity implements Serializable {
	
	@NotNull(message = "Number can not be empty")
	@Column(nullable = false)
	private String number;
	
	@NotNull(message = "City Code can not be empty")
	@Column(nullable = false)
	private String citycode;
	
	@NotNull(message = "Contry Code can not be empty")
	@Column(nullable = false)
	private String contrycode;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="user_id")
	private User user;
	
	private static final long serialVersionUID = -2794279525903621708L;

	public Phone() {
	}

	@JsonIgnore
	public UUID getId() {
		return getId();
	}
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
