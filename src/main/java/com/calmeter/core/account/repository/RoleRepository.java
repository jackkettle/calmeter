package com.calmeter.core.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calmeter.core.account.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}