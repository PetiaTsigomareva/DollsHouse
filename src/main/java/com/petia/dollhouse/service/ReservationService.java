package com.petia.dollhouse.service;

import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.domain.view.AllReservationViewModel;

import java.util.List;

public interface ReservationService {

    String add(ReservationServiceModel model);

    String addModeratorReservation(ReservationServiceModel model);

    ReservationServiceModel edit(ReservationServiceModel model);

    List<ReservationServiceModel> findAll();

    ReservationServiceModel findByID(String id);

    void setReservationStatus(String id, String status);

    List<AllReservationViewModel> mapServiceToViewModels(List<ReservationServiceModel> models);

    ReservationServiceModel mapBindingToServiceModel(ReservationBindingModel model);

    List<String> getReservationStatusValues();
}
