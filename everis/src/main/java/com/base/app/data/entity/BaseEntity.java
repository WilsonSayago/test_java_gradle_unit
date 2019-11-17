package com.base.app.data.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", length = 255)
    private UUID id;
	
	protected Date created;

	private Date modified;

	private boolean isactive;
	
	@JsonIgnore
    @Version
    private Integer version = 0;

	public BaseEntity() {
	}
	
	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public Date getModified() {
		return modified;
	}



	public void setModified(Date modified) {
		this.modified = modified;
	}



	public boolean isIsactive() {
		return isactive;
	}



	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}



	public Integer getVersion() {
		return version;
	}



	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@PrePersist
	private void prePersist() {
		this.isactive = true;
		Date date = new Date();
		this.created = date;
		this.modified = date;
	}
	
	@PostPersist
	private void postPersist() {
		this.modified = new Date();
	}
}
