package com.petia.dollhouse.service;

import com.petia.dollhouse.domain.service.CompanyServiceModel;

import java.util.List;

public interface CompanyService {

    String addCompany(CompanyServiceModel model);

    CompanyServiceModel editCompany(CompanyServiceModel model);

    List<CompanyServiceModel> findAllCompanies();

    CompanyServiceModel findCompanyByID(String id);

    CompanyServiceModel deleteCompany(CompanyServiceModel companyServiceModel);
}
