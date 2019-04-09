package com.petia.dollhouse.service;

import com.petia.dollhouse.domain.service.ReservationServiceModel;

import java.util.List;

public interface ReservationService {

    String add(ReservationServiceModel model);
    String addModeratorReservation(ReservationServiceModel model);

    ReservationServiceModel edit(ReservationServiceModel model);

    List<ReservationServiceModel> findAll();

    ReservationServiceModel findByID(String id);

    ReservationServiceModel delete(ReservationServiceModel model);
}
