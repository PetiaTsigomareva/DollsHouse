package com.petia.dollhouse.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.Company;
import com.petia.dollhouse.domain.enums.StatusValues;
import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;
import com.petia.dollhouse.repositories.CompanyRepository;
import com.petia.dollhouse.validation.ValidationUtil;

@Service
public class CompanyServiceImpl implements CompanyService {
	private final CompanyRepository companyRepository;
	private final ModelMapper modelMapper;
	private final ValidationUtil validationUtil;

	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
		this.companyRepository = companyRepository;
		this.modelMapper = modelMapper;
		this.validationUtil = validationUtil;
	}

	@Override
	public String addCompany(CompanyServiceModel model) {
		String result;
		if (!this.validationUtil.isValid(model)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		Company company = this.modelMapper.map(model, Company.class);
		company.setStatus(StatusValues.ACTIVE);

		if (this.companyRepository.findCompanyByName(company.getName()).orElse(null) != null) {
			throw new IllegalArgumentException(Constants.EXIST_ITEM_ERROR_MESSAGE);
		}

		company = this.companyRepository.saveAndFlush(company);
		result = company.getId();

		return result;
	}

	@Override
	public CompanyServiceModel editCompany(CompanyServiceModel companyServiceModel) {
		if (!this.validationUtil.isValid(companyServiceModel)) {
			throw new IllegalArgumentException(Constants.ADD_INVALID_DATA_IN_CONTROLLER_MESSAGE);
		}

		Company company = this.companyRepository.findById(companyServiceModel.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		companyServiceModel.setStatus(company.getStatus().name());
		Company mappedCompany = this.companyRepository.saveAndFlush(this.modelMapper.map(companyServiceModel, Company.class));

		companyServiceModel = this.modelMapper.map(mappedCompany, CompanyServiceModel.class);

		return companyServiceModel;
	}

	@Override
	public List<CompanyServiceModel> findAllCompanies() {
		List<Company> companies = this.companyRepository.findAllActiveCompanies();

		List<CompanyServiceModel> companiesModel = companies.stream().map(c -> this.modelMapper.map(c, CompanyServiceModel.class)).collect(Collectors.toList());

		return companiesModel;
	}

	@Override
	public CompanyServiceModel findCompanyByID(String id) {
		Company company = this.companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		CompanyServiceModel companyServiceModel = this.modelMapper.map(company, CompanyServiceModel.class);

		return companyServiceModel;
	}

	@Override
	public CompanyServiceModel deleteCompany(CompanyServiceModel companyServiceModel) {
		Company company = this.companyRepository.findById(companyServiceModel.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

		company.setStatus(StatusValues.INACTIVE);

		company = this.companyRepository.saveAndFlush(company);

		companyServiceModel = this.modelMapper.map(company, CompanyServiceModel.class);

		return companyServiceModel;
	}

	@Override
	public CompanyServiceModel findCompanyByName(String name) {
		CompanyServiceModel result;
		Company company = this.companyRepository.findCompanyByName(name).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));
		result = this.modelMapper.map(company, CompanyServiceModel.class);
		return result;
	}

	public List<NamesViewModel> mapCompanyNames() {
		List<NamesViewModel> result;
		result = findAllCompanies().stream().map(c -> this.modelMapper.map(c, NamesViewModel.class)).collect(Collectors.toList());

		return result;
	}
}