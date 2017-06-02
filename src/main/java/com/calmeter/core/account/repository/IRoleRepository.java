package com.calmeter.core.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calmeter.core.account.model.UserRole;


public interface IRoleRepository extends JpaRepository<UserRole, Long> {
}