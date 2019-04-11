package com.petia.dollhouse.domain.view;

import com.petia.dollhouse.constants.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AllReservationViewModel {
    private String office;
    private String service;
    private String employee;
    private String customer;
    @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime reservationDateTime;

    public AllReservationViewModel() {
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }
}
