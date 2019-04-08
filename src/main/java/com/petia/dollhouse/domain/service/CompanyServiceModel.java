package com.petia.dollhouse.domain.service;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CompanyServiceModel extends BaseServiceModel {
    private String name;
    private String address;
    private String identificationCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfCreation;
    private String owner;
    private String description;
    private Set<String> offices;

    public CompanyServiceModel() {
        offices=new HashSet<>();
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
