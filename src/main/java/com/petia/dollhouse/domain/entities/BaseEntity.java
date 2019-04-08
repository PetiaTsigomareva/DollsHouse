package com.petia.dollhouse.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable {
	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid-string")
	@GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private String id;

	public BaseEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
