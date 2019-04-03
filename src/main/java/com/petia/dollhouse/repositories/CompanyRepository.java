package com.petia.dollhouse.repositories;

import com.petia.dollhouse.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Company findFirstByOwner(String name);

    @Query("SELECT c FROM Company c WHERE c.status = 'ACTIVE'")
    List<Company> findAllActiveCompanies();

//    @Query("SELECT c FROM Company c WHERE c.status = 'ACTIVE'")
//    List<String> findAllCompanyNames();

}
