package com.petia.dollhouse.domain.service;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.petia.dollhouse.constants.Constants;

public class ServiceModel extends BaseServiceModel {
	@NotNull()
	@NotEmpty()
	@Pattern(regexp = Constants.MIN_TEXT_FIELD_REGEX)
	private String name;

	private String description;

	@DecimalMin(value = "0.01")
	private BigDecimal price;

	private String urlPicture;

	@NotNull()
	@NotEmpty()
	private String officeId;

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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public ServiceModel() {
		super();
	}

}
