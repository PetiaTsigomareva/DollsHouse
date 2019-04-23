package com.petia.dollhouse.domain.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

public class CompanyServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	@DateTimeFormat(pattern = Constants.MIN_TEXT_FOUR_FIELD_REGEX)
	@Size(min = 4)
	private String name;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.ADDRESS_REGEX)
	private String address;

	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.ID_NUMBER_REGEX)
	private String identificationCode;

	@NotNull
	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	private LocalDate dateOfCreation;

	@NotNull()
	@NotEmpty()
	@DateTimeFormat(pattern = Constants.MIN_TEXT_FOUR_FIELD_REGEX)
	@Size(min = 4)
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
