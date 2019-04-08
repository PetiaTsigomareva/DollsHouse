package com.petia.dollhouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.petia.dollhouse.domain.enums.StatusValues;

@MappedSuperclass
public class EntityWithStatus extends BaseEntity {
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusValues status;

	public EntityWithStatus() {
		setStatus(StatusValues.ACTIVE);
	}

	public StatusValues getStatus() {
		return status;
	}

	public void setStatus(StatusValues status) {
		this.status = status;
	}
}
