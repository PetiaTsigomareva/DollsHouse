package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.binding.ReservationBindingModel;
import com.petia.dollhouse.domain.service.ReservationServiceModel;
import com.petia.dollhouse.domain.view.AllReservationViewModel;

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

	List<ReservationServiceModel> findALLByUser(String username);
}
