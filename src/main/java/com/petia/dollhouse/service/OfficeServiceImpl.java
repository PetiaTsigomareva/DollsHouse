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
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.repositories.OfficeRepository;
import com.petia.dollhouse.validation.ValidationUtil;

@Service
public class OfficeServiceImpl implements OfficeService {
	private final CompanyService companyService;
	private final OfficeRepository officeRepository;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;

	@Autowired
	public OfficeServiceImpl(CompanyService companyService, OfficeRepository officeRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.companyService = companyService;
		this.officeRepository = officeRepository;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}

	@Override
	public String addOffice(OfficeServiceModel model) {
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		String result;

		Company company = findCompany(model.getCompanyId());
		Office office = this.modelMapper.map(model, Office.class);
		office.setCompany(company);
		office.setStatus(StatusValues.ACTIVE);

		if (this.officeRepository.findByName(office.getName()).orElse(null) != null) {
			throw new IllegalArgumentException(Constants.EXIST_ITEM_ERROR_MESSAGE);
		}

		office = this.officeRepository.saveAndFlush(office);
		result = office.getId();

		return result;
	}

	@Override
	public OfficeServiceModel editOffice(OfficeServiceModel model) {
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		Office office = this.officeRepository.findById(model.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		model.setStatus(office.getStatus().name());

		Office officeNew = this.modelMapper.map(model, Office.class);
		officeNew.setCompany(this.findCompany(model.getCompanyId()));
		officeNew = this.officeRepository.saveAndFlush(officeNew);

		model = this.modelMapper.map(officeNew, OfficeServiceModel.class);

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

	public List<NamesViewModel> mapOfficeNames() {
		List<NamesViewModel> result;
		result = findAllOffices().stream().map(o -> this.modelMapper.map(o, NamesViewModel.class)).collect(Collectors.toList());

		return result;
	}
}