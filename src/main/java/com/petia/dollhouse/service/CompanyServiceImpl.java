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
import com.petia.dollhouse.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addCompany(CompanyServiceModel model) {
        String result;
        try {
            Company company = this.modelMapper.map(model, Company.class);
            company.setStatus(StatusValues.ACTIVE);
            company = this.companyRepository.saveAndFlush(company);
            result = company.getId();

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    public CompanyServiceModel editCompany(CompanyServiceModel companyServiceModel) {

        Company company = this.companyRepository.findById(companyServiceModel.getId()).orElseThrow(() -> new NoSuchElementException(Constants.ERROR_MESSAGE));

        Company mappedCompany = this.companyRepository.saveAndFlush(this.modelMapper.map(companyServiceModel, Company.class));
        mappedCompany.setStatus(company.getStatus());

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

}
