package com.petia.dollhouse.domain.view;

public class CompanyNameViewModel extends BaseViewModel {
    private String name;

    public CompanyNameViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String selected(String compayId) {
    	String result;
    	
    	if (getId().equals(compayId)) {
    		result = "selected";
    	}else {
    		result="";
    	}
    	
    	return result;
    }
}
