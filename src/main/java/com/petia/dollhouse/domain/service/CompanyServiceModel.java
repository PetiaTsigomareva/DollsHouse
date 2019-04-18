package com.petia.dollhouse.domain.service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.constants.ValidatedConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CompanyServiceModel extends BaseServiceModel {
    @NotNull()
    @NotEmpty()
    private String name;

    @NotNull()
    @NotEmpty()
    private String address;

    @NotNull()
    @NotEmpty()
    @Size(min = 9, max = 9)
    private String identificationCode;


    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate dateOfCreation;

    @NotNull()
    @NotEmpty()
    private String owner;

    private String description;
    private Set<String> offices;

    public CompanyServiceModel() {
        offices = new HashSet<>();
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

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getOffices() {
        return offices;
    }

    public void setOffices(Set<String> offices) {
        this.offices = offices;
    }
}
