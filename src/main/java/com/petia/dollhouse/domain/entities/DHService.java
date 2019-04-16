package com.petia.dollhouse.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class DHService extends EntityWithStatus {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2) default '00.00'")
	private BigDecimal price;

	@Column(name = "picture", nullable = false)
	private String urlPicture;

	@OneToMany(targetEntity = Reservation.class, mappedBy = "service")
	private List<Reservation> reservations;

//	@ManyToMany(targetEntity = PromoOffer.class)
//	@JoinTable(name = "promo_offers_services", joinColumns = @JoinColumn(name = "promo_offers_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
//	private List<PromoOffer> offers;

	@ManyToOne(targetEntity = Office.class)
	@JoinColumn(name = "office_id", referencedColumnName = "id")
	private Office office;

	@OneToMany(targetEntity = User.class, mappedBy = "employeeService")
	private List<User> employees;

	public DHService() {
		super();
	//	offers = new ArrayList<>();
		reservations = new ArrayList<>();
		employees = new ArrayList<>();
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

	public String getUrlPicture() {
		return urlPicture;
	}

	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

//	public List<PromoOffer> getOffers() {
//		return offers;
//	}
//
//	public void setOffers(List<PromoOffer> offers) {
//		this.offers = offers;
//	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
}
