package com.petia.dollhouse.domain.binding;

import java.math.BigDecimal;

import com.petia.dollhouse.constants.ValidatedConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ServiceBindingModel {
	private String serviceId;

	@NotNull()
	@NotEmpty()
	private String name;

	private String description;

	@NotNull()
	@NotEmpty()
	@DecimalMin(value = "0.01")
	private BigDecimal price;

	private MultipartFile image;

	@NotNull()
	@NotEmpty()
	private String officeId;

	public ServiceBindingModel() {
		super();
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}