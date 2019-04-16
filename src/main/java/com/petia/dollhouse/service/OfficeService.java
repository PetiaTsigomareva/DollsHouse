package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;

public interface OfficeService {

	String addOffice(OfficeServiceModel model);

	OfficeServiceModel editOffice(OfficeServiceModel model);

	List<OfficeServiceModel> findAllOffices();

	OfficeServiceModel findOfficeByID(String id);

	OfficeServiceModel deleteOffice(OfficeServiceModel model);

	List<NamesViewModel> mapOfficeNames();
}