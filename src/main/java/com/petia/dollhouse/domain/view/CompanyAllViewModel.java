package com.petia.dollhouse.domain.view;

public class CompanyAllViewModel extends BaseViewModel {
    private String name;
    private String address;
    private String owner;


    public CompanyAllViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
