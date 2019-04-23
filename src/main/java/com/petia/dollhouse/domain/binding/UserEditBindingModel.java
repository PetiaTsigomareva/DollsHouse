package com.petia.dollhouse.domain.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.petia.dollhouse.constants.Constants;

public class UserEditBindingModel {
	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.USERNAME_REGEX)
	private String username;

//	@NotNull()
//	@NotEmpty()
//	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String oldPassword;

	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String password;

	@Pattern(regexp = Constants.PASSWORD_REGEX)
	private String confirmPassword;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.EMAIL_REGEX)
	private String email;

	private MultipartFile image;

	public UserEditBindingModel() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
