package com.petia.dollhouse.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

@Entity
@Table(name = "services")
public class DHService extends EntityWithStatus {

	@Column(name = "name", nullable = false)
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2) default '00.00'")
	private BigDecimal price;

	@Column(name = "picture")
	private String urlPicture;

	@OneToMany(targetEntity = Reservation.class, mappedBy = "service")
	private List<Reservation> reservations;

	@ManyToOne(targetEntity = Office.class)
	@JoinColumn(name = "office_id", referencedColumnName = "id")
	private Office office;

	@OneToMany(targetEntity = User.class, mappedBy = "service")
	private List<User> employees;

	public DHService() {
		super();

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

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
}
