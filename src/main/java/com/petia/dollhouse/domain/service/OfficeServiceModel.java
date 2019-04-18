package com.petia.dollhouse.domain.service;

import com.petia.dollhouse.constants.ValidatedConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfficeServiceModel extends BaseServiceModel {

	@NotNull()
	@NotEmpty()
	private String name;

	@NotNull()
	@NotEmpty()
	private String address;

	@NotNull()
	@NotEmpty()
	@Size(min = 7,max = 12)
	private String phoneNumber;

	@NotNull()
	@NotEmpty()
	@Size(min = 6)//TODO
	private String email;

	@NotNull()
	@NotEmpty()
	private String companyId;

	public OfficeServiceModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
