package com.petia.dollhouse.domain.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

@Entity
@Table(name = "company")
public class Company extends EntityWithStatus {

	@Column(name = "name", nullable = false, unique = true)
	@DateTimeFormat(pattern = Constants.MIN_TEXT_FOUR_FIELD_REGEX)
	private String name;

	@Column(name = "address", nullable = false)
	@Pattern(regexp = Constants.ADDRESS_REGEX)
	private String address;

	@Column(name = "identification_code", nullable = false, length = 9)
	private String identificationCode;

	@Column(name = "date_of_creation", nullable = false)
	private LocalDate dateOfCreation;

	@Column(name = "owner", nullable = false)
	@DateTimeFormat(pattern = Constants.MIN_TEXT_FOUR_FIELD_REGEX)
	private String owner;

	@OneToMany(targetEntity = Office.class, mappedBy = "company")
	private Set<Office> offices;

	@Column(name = "description")
	private String description;

	public Company() {
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

	public Set<Office> getOffices() {
		return offices;
	}

	public void setOffices(Set<Office> offices) {
		this.offices = offices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
