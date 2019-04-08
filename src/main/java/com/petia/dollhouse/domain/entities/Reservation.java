package com.petia.dollhouse.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.petia.dollhouse.domain.enums.ReservationStatus;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private User employee;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private User customer;

	@ManyToMany(targetEntity = DHService.class)
	@JoinTable(name = "reservations_services", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
	private List<DHService> services;

	@ManyToMany(targetEntity = PromoOffer.class)
	@JoinTable(name = "reservations_offers", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "id"))
	private List<PromoOffer> offers;

	@Column(name = "reservation_date_time", nullable = false)
	private LocalDateTime reservationDateTime;

	@Column(name = "description", nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

	public Reservation() {
		super();
		services = new ArrayList<>();
		offers = new ArrayList<>();
		status = ReservationStatus.PENDING_CONFIRMATION;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public List<DHService> getServices() {
		return services;
	}

	public void setServices(List<DHService> services) {
		this.services = services;
	}

	public List<PromoOffer> getOffers() {
		return offers;
	}

	public void setOffers(List<PromoOffer> offers) {
		this.offers = offers;
	}

	public LocalDateTime getReservationDateTime() {
		return reservationDateTime;
	}

	public void setReservationDateTime(LocalDateTime reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
}