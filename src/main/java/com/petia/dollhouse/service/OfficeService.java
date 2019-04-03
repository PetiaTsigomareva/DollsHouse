package com.petia.dollhouse.service;

import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.service.OfficeServiceModel;

import java.util.List;

public interface OfficeService {

    String addOffice(OfficeServiceModel model);

    OfficeServiceModel editOffice(OfficeServiceModel model);

    List<OfficeServiceModel> findAllOffices();

    OfficeServiceModel findOfficeByID(String id);

    OfficeServiceModel deleteOffice(OfficeServiceModel model);




}
