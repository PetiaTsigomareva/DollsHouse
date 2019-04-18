package com.petia.dollhouse.domain.service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.constants.ValidatedConstants;
import com.petia.dollhouse.domain.entities.DHService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PromoOfferServiceModel extends BaseServiceModel {
    @NotNull()
    @NotEmpty()
    private String name;

    private String description;

    @NotNull()
    @DecimalMin(value = "0.01")
    private BigDecimal price;


    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate startDate;


    @DateTimeFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate endDate;


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


}
