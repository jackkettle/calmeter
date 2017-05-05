package com.calmeter.core.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.external.model.FoodSource;

public interface IFoodSourceRepository extends JpaRepository<FoodSource, Long> {
}
