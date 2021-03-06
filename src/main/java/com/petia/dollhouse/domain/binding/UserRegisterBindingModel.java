package com.petia.dollhouse.domain.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

public class UserRegisterBindingModel {
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

	public UserRegisterBindingModel() {
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
}
