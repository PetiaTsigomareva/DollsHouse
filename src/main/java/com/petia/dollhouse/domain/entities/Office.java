package com.petia.dollhouse.domain.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

@Entity
@Table(name = "offices")
public class Office extends EntityWithStatus {

	@Column(name = "name", nullable = false, unique = true)
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String name;

	@Column(name = "address", nullable = false)
	@Pattern(regexp = Constants.ADDRESS_REGEX)
	private String address;

	@Column(name = "phone_number", nullable = false)
//	@DateTimeFormat(pattern = Constants.PHONE_NUMBER_REGEX)
	private String phoneNumber;

	@Column(name = "email", nullable = false, unique = true)
	@Pattern(regexp = Constants.EMAIL_REGEX)
	private String email;

	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@OneToMany(targetEntity = User.class, mappedBy = "office")
	private List<User> employees;

	@OneToMany(targetEntity = DHService.class, mappedBy = "office")
	private Set<DHService> services;

	@OneToMany(targetEntity = Reservation.class, mappedBy = "office")
	private List<Reservation> reservations;

	public Office() {
		employees = new ArrayList<>();
		services = new HashSet<>();
		reservations = new ArrayList<>();
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<User> getEmployees() {
		return employees;
	}

	public void setEmployees(List<User> employees) {
		this.employees = employees;
	}

	public Set<DHService> getServices() {
		return services;
	}

	public void setServices(Set<DHService> services) {
		this.services = services;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}