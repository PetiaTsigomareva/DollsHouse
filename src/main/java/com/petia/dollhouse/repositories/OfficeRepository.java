package com.petia.dollhouse.repositories;


import com.petia.dollhouse.domain.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

    @Query("SELECT o FROM Office o WHERE o.status = 'ACTIVE'")
    List<Office> findAllActiveOffices();
}

