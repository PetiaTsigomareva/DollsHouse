package com.petia.dollhouse.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
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

	@ManyToOne(targetEntity = DHService.class)

	@JoinColumn(name = "service_id", referencedColumnName = "id")
	private DHService service;

	@ManyToOne(targetEntity = PromoOffer.class)
	@JoinColumn(name = "offer_id", referencedColumnName = "id")
	private PromoOffer offer;

	@Column(name = "reservation_date_time", nullable = false)
	private LocalDateTime reservationDateTime;

	@Column(name = "description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

	@ManyToOne(targetEntity = Office.class)
	private Office office;

	public Reservation() {
		super();
		status = ReservationStatus.PendingConfirmation;
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

	public DHService getService() {
		return service;
	}

	public void setService(DHService service) {
		this.service = service;
	}

	public PromoOffer getOffer() {
		return offer;
	}

	public void setOffer(PromoOffer offer) {
		this.offer = offer;
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

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
}