package com.petia.dollhouse.domain.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.petia.dollhouse.constants.Constants;

public class EmployeeBindingModel {
	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.USERNAME_REGEX)
	private String username;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String password;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String confirmPassword;

	@NotNull()
	@NotEmpty()
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

	private MultipartFile image;

	private String officeId;

	private String serviceId;

	public EmployeeBindingModel() {
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}