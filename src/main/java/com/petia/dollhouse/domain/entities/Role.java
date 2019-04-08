package com.petia.dollhouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role extends EntityWithStatus implements GrantedAuthority {

	@Column(name = "authority", nullable = false)
	private String authority;

	public Role() {
		super();
	}

	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
