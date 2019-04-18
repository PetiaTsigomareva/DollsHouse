package com.petia.dollhouse.domain.service;

import com.petia.dollhouse.constants.ValidatedConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	@Size(min = 5, max = 10)
	private String username;

	@NotNull()
	@NotEmpty()
	@Size(min = 6)//TODO
	private String password;


    @Size(min = 6)//TODO
	private String email;

	@NotNull()
	@NotEmpty()
	private String firstName;

	@NotNull()
	@NotEmpty()
	private String lastName;

	@NotNull()
	@NotEmpty()
    @Size(min = 7,max = 12)
	private String phoneNumber;

	private String imageUrl;

	private String officeId;

	private String serviceId;

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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}