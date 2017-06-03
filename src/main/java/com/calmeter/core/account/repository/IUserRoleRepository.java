package com.calmeter.core.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.UserRole;


public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {
	
	Optional<UserRole> findByRole (Role role);
	
}