package com.petia.dollhouse.domain.view;

import java.util.ArrayList;
import java.util.List;

public class AboutViewModel{
    private String companyDescription;
    private List<AllEmployeeViewModel> employeeViewModels;

    public AboutViewModel() {
        employeeViewModels=new ArrayList<>();
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public List<AllEmployeeViewModel> getEmployeeViewModels() {
        return employeeViewModels;
    }

    public void setEmployeeViewModels(List<AllEmployeeViewModel> employeeViewModels) {
        this.employeeViewModels = employeeViewModels;
    }
}
