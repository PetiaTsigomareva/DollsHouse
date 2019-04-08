package com.petia.dollhouse.service;

import java.time.LocalDate;
import java.util.List;

import com.petia.dollhouse.domain.service.DayAvailabilityServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;

public interface DollHouseService {

	String add(ServiceModel model);

	ServiceModel edit(ServiceModel model);

	List<ServiceModel> findAll();

	ServiceModel findByID(String id);

	ServiceModel delete(ServiceModel model);

	List<ServiceModel> findServicesByOffice(String officeId);

	List<DayAvailabilityServiceModel> fetchAvailabilities(String serviceId, String emloyeeId, LocalDate fromDate, LocalDate toDate);
}
