package com.petia.dollhouse.service;

import java.util.List;

import com.petia.dollhouse.domain.service.CompanyServiceModel;
import com.petia.dollhouse.domain.view.NamesViewModel;

public interface CompanyService {

	String addCompany(CompanyServiceModel model);

	CompanyServiceModel editCompany(CompanyServiceModel model);

	List<CompanyServiceModel> findAllCompanies();

	CompanyServiceModel findCompanyByID(String id);

	CompanyServiceModel deleteCompany(CompanyServiceModel companyServiceModel);

	CompanyServiceModel findCompanyByName(String name);

	List<NamesViewModel> mapCompanyNames();

}
