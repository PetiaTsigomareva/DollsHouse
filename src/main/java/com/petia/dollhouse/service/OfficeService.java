package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.service.OfficeServiceModel;

public interface OfficeService {

	String addOffice(OfficeServiceModel model);

	OfficeServiceModel editOffice(OfficeServiceModel model);

	List<OfficeServiceModel> findAllOffices();

	OfficeServiceModel findOfficeByID(String id);

	OfficeServiceModel deleteOffice(OfficeServiceModel model);

}
