package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.service.AvailabilityServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;

public interface DollHouseService {

	String add(ServiceModel model);

	ServiceModel edit(ServiceModel model);

	List<ServiceModel> findAll();

	ServiceModel findByID(String id);

	ServiceModel delete(ServiceModel model);

	List<ServiceModel> findServicesByOffice(String officeId);

	List<AvailabilityServiceModel> fetchAvailabilities(String officeId, String serviceId, String fromDate, String toDate);
}
