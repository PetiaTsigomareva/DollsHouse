package com.petia.dollhouse.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.office is not null and u.status='ACTIVE'")
	List<User> findAllEmployees();

	// TODO
	@Query("SELECT u FROM User u WHERE u.office is null and u.status='ACTIVE'")
	List<User> findAllCustomers();

	@Query("SELECT u FROM User u WHERE u.office.id=:id")
	List<User> findAllEmployeesByOffice(String id);

	@Query("SELECT u FROM User u inner join u.service s WHERE s.id=:serviceId")
	List<User> findAllEmployeesByService(String serviceId);
}
