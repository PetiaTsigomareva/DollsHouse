package com.petia.dollhouse.domain.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.petia.dollhouse.domain.enums.StatusValues;

@Entity
@Table(name = "company")
public class Company extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "identification_code", nullable = false)
	private String identificationCode;

	@Column(name = "date_of_creation", nullable = false)
	private LocalDate dateOfCreation;

	@Column(name = "owner", nullable = false)
	private String owner;

	@OneToMany(targetEntity = Office.class, mappedBy = "company")
	private Set<Office> offices;

//	@Enumerated(EnumType.STRING)
//	@Column(name = "status")
//	private StatusValues status;

	public Company() {
		offices = new HashSet<>();
		//setStatus(StatusValues.ACTIVE);
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

//	public StatusValues getStatus() {
//		return status;
//	}
//
//	public void setStatus(StatusValues status) {
//		this.status = status;
//	}
}
