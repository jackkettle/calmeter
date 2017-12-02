package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.Meal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMealRepository extends JpaRepository<Meal, Long> {

    Optional<Meal> findById(Long id);

    Optional<Meal> findByName(String name);

    List<Meal> findAllByCreator(User user);

    List<Meal> findByNameContainingIgnoreCaseAndDisabled(String searchString, boolean showDisabled);

    List<Meal> findByNameContainingIgnoreCaseAndDisabledAndCreator(String searchString, boolean showDisabled, User user);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Meal m WHERE m.name = :name")
    Boolean existsByName(@Param("name") String name);

}