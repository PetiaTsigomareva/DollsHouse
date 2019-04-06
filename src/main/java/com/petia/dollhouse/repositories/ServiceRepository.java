package com.petia.dollhouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.DHService;

@Repository
public interface ServiceRepository extends JpaRepository<DHService, String> {

	@Query("SELECT s FROM DHService s WHERE s.status = 'ACTIVE'")
	List<DHService> findAllActiveService();

}
