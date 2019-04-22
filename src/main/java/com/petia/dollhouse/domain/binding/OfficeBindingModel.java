package com.petia.dollhouse.domain.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

public class OfficeBindingModel {
	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String name;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.ADDRESS_REGEX)
	private String address;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
	private String phoneNumber;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.EMAIL_REGEX)
	private String email;

	@NotNull()
	@NotEmpty()
	private String companyId;

	public OfficeBindingModel() {
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