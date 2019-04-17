package com.petia.dollhouse.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {

	@Query("SELECT o FROM Office o WHERE o.status = 'ACTIVE'")
	List<Office> findAllActiveOffices();

    Optional<Office> findByName(String name);
}
