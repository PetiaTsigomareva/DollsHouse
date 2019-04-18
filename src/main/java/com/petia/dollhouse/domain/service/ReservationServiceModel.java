package com.petia.dollhouse.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.petia.dollhouse.constants.ValidatedConstants;
import com.petia.dollhouse.domain.enums.ReservationStatus;
import com.petia.dollhouse.validation.EnumValidation;
import org.springframework.format.annotation.DateTimeFormat;

import com.petia.dollhouse.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReservationServiceModel extends BaseServiceModel {
    @NotNull()
    @NotEmpty()
    private String officeId;

    @NotNull()
    @NotEmpty()
    private String serviceId;

    @NotNull()
    @NotEmpty()
    private String employeeId;

    @NotNull()
    @NotEmpty()
    private String customerId;


    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime reservationDateTime;

    private String description;


    @EnumValidation(enumClass = ReservationStatus.class, ignoreCase = true)
    private String reservationStatus;

    public ReservationServiceModel() {

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
