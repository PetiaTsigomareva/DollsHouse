package com.petia.dollhouse.domain.binding;

import com.petia.dollhouse.constants.ValidatedConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditBindingModel {
    @NotNull()
    @NotEmpty()
    @Size(min = 5, max = 10)
    private String username;

    @NotNull()
    @NotEmpty()
    @Size(min = 6)//TODO
    private String oldPassword;

    @NotNull()
    @NotEmpty()
    @Size(min = 6)//TODO
    private String password;

    @NotNull()
    @NotEmpty()
    @Size(min = 6)//TODO
    private String confirmPassword;

    @NotNull()
    @NotEmpty()
    @Size(min = 6)//TODO
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
