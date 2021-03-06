package com.petia.dollhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petia.dollhouse.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByAuthority(String authority);
}
