package com.petia.dollhouse.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.repositories.ServiceRepository;

@Service
public class DollHouseServiceImpl implements DollHouseService {
	private final ServiceRepository serviceRepository;
	private final OfficeService officeService;
	private final ModelMapper modelMapper;

	@Autowired
	public DollHouseServiceImpl(ServiceRepository serviceRepository, OfficeService officeService, ModelMapper modelMapper) {
		super();
		this.serviceRepository = serviceRepository;
		this.officeService = officeService;
		this.modelMapper = modelMapper;
	}

	@Override
	public String add(ServiceModel model) {
		String result;
		try {

			DHService service = this.modelMapper.map(model, DHService.class);
			service.setOffice(this.findOffice(model.getOfficeId()));
			service.setStatus(StatusValues.ACTIVE);
			service = this.serviceRepository.saveAndFlush(service);
			result = service.getId();

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			result = null;
		}

		return result;
	}

	@Override
	public ServiceModel edit(ServiceModel model) {

		DHService service = this.serviceRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		model.setStatus(service.getStatus().name());

		DHService serviceNew = this.modelMapper.map(model, DHService.class);
		serviceNew.setOffice(this.findOffice(model.getOfficeId()));
		DHService mappedService = this.serviceRepository.saveAndFlush(serviceNew);

		model = this.modelMapper.map(mappedService, ServiceModel.class);

		return model;
	}

	@Override
	public List<ServiceModel> findAll() {
		List<DHService> services = this.serviceRepository.findAllActiveServices();

		List<ServiceModel> servicesModel = services.stream().map(s -> this.modelMapper.map(s, ServiceModel.class)).collect(Collectors.toList());

		return servicesModel;


	}

	@Override
	public ServiceModel findByID(String id) {
		DHService service = this.serviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		ServiceModel model = this.modelMapper.map(service, ServiceModel.class);

		return model;
	}

	@Override
	public ServiceModel delete(ServiceModel model) {
		DHService service = this.serviceRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		service.setStatus(StatusValues.INACTIVE);

		service = this.serviceRepository.saveAndFlush(service);

		model = this.modelMapper.map(service, ServiceModel.class);

		return model;
	}

	private Office findOffice(String id) {
		Office office = this.modelMapper.map(this.officeService.findOfficeByID(id), Office.class);

		return office;

	}

}
