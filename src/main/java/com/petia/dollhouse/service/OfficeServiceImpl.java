package com.petia.dollhouse.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.Company;
import com.petia.dollhouse.domain.entities.Office;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.OfficeServiceModel;
import com.petia.dollhouse.repositories.OfficeRepository;

@Service
public class OfficeServiceImpl implements OfficeService {
	private final CompanyService companyService;
	private final OfficeRepository officeRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public OfficeServiceImpl(CompanyService companyService, OfficeRepository officeRepository, ModelMapper modelMapper) {
		this.companyService = companyService;
		this.officeRepository = officeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public String addOffice(OfficeServiceModel model) {
		String result;
		try {
			Company company = findCompany(model.getCompanyId());
			Office office = this.modelMapper.map(model, Office.class);
			office.setCompany(company);
			office = this.officeRepository.saveAndFlush(office);
			result = office.getId();

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			result = null;
		}

		return result;
	}

	@Override
	public OfficeServiceModel editOffice(OfficeServiceModel model) {
		Office office = this.officeRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		model.setStatus(office.getStatus().name());

		Office officeNew = this.modelMapper.map(model, Office.class);
		officeNew.setCompany(this.findCompany(model.getCompanyId()));
		Office mappedOffice = this.officeRepository.saveAndFlush(officeNew);

		model = this.modelMapper.map(mappedOffice, OfficeServiceModel.class);

		return model;
	}

	@Override
	public List<OfficeServiceModel> findAllOffices() {
		List<Office> offices = this.officeRepository.findAllActiveOffices();

		List<OfficeServiceModel> officesModel = offices.stream().map(o -> this.modelMapper.map(o, OfficeServiceModel.class)).collect(Collectors.toList());

		return officesModel;
	}

	@Override
	public OfficeServiceModel findOfficeByID(String id) {
		Office office = this.officeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		OfficeServiceModel model = this.modelMapper.map(office, OfficeServiceModel.class);

		return model;
	}

	@Override
	public OfficeServiceModel deleteOffice(OfficeServiceModel model) {
		Office office = this.officeRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		office.setStatus(StatusValues.INACTIVE);

		office = this.officeRepository.saveAndFlush(office);

		model = this.modelMapper.map(office, OfficeServiceModel.class);

		return model;
	}

	private Company findCompany(String id) {
		Company company;
		company = this.modelMapper.map(this.companyService.findCompanyByID(id), Company.class);

		return company;

	}

}
