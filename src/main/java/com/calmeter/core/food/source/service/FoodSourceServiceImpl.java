package com.calmeter.core.food.source.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.repository.IFoodSourceRepository;

@Component("foodSourceService")
public class FoodSourceServiceImpl
        implements IFoodSourceService {

    @Autowired
    IFoodSourceRepository foodSourceRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodSource> findByName(String name) {
        return foodSourceRepository.findByName(name);

    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodSource> getExternalSources() {
        return foodSourceRepository.findAllByExternalSourceOrderByExternalPriority(true);

    }

}
