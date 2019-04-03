package com.petia.dollhouse.domain.service;

public class BaseServiceModel {

	private String id;
	private String status;

	protected BaseServiceModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}