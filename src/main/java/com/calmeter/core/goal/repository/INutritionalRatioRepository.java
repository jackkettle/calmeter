package com.calmeter.core.goal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.NutritionalRatio;

public interface INutritionalRatioRepository extends JpaRepository<NutritionalRatio, Long> {

	List<NutritionalRatio> findByCreator(User user);
	
	List<NutritionalRatio> findByGlobal(Boolean global);
	
	Optional<NutritionalRatio> findById(Long id);

}
