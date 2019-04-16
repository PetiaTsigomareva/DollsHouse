package com.petia.dollhouse.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "promo_offers")
public class PromoOffer extends EntityWithStatus {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private BigDecimal price;

//	@Column(name = "picture")
//	private String urlPicture;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

//	@ManyToMany(targetEntity = DHService.class, mappedBy = "offers")
//	private List<DHService> services;

	@OneToMany(targetEntity = Reservation.class, mappedBy = "offer")
	private List<Reservation> reservations;

	public PromoOffer() {

		//services = new ArrayList<>();
		reservations = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

//	public String getUrlPicture() {
//		return urlPicture;
//	}
//
//	public void setUrlPicture(String urlPicture) {
//		this.urlPicture = urlPicture;
//	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

//	public List<DHService> getServices() {
//		return services;
//	}
//
//	public void setServices(List<DHService> services) {
//		this.services = services;
//	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
