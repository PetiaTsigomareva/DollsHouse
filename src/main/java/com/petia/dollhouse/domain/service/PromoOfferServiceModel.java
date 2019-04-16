package com.petia.dollhouse.domain.service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.DHService;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PromoOfferServiceModel extends BaseServiceModel{

    private String name;
    private String description;
    private BigDecimal price;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate startDate;
    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate endDate;
    private List<String> services;

    public PromoOfferServiceModel() {
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

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
