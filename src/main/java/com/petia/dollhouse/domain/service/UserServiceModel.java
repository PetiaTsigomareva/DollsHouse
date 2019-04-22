package com.petia.dollhouse.domain.service;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

public class UserServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.USERNAME_REGEX)
	private String username;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String password;

	@Pattern(regexp = Constants.EMAIL_REGEX)
	private String email;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String firstName;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String lastName;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
	private String phoneNumber;

	private String imageUrl;

	private OfficeServiceModel officeServiceModel;

	private ServiceModel serviceModel;

	private Set<RoleServiceModel> authorities;

	public UserServiceModel() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<RoleServiceModel> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<RoleServiceModel> authorities) {
		this.authorities = authorities;
	}

	public OfficeServiceModel getOfficeServiceModel() {
		return officeServiceModel;
	}

	public void setOfficeServiceModel(OfficeServiceModel officeServiceModel) {
		this.officeServiceModel = officeServiceModel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ServiceModel getServiceModel() {
		return serviceModel;
	}

	public void setServiceModel(ServiceModel serviceModel) {
		this.serviceModel = serviceModel;
	}
}