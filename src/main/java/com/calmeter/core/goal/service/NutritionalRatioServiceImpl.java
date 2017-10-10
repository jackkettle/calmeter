package com.calmeter.core.goal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.NutritionalRatio;
import com.calmeter.core.goal.repository.INutritionalRatioRepository;

@Component("nutritionalRatioService")
@Transactional
public class NutritionalRatioServiceImpl implements INutritionalRatioService{

	@Autowired
	INutritionalRatioRepository nutritionalRatioRepository;
	
	@Override
	public Optional<NutritionalRatio> getRatio(Long id) {
		return nutritionalRatioRepository.findById(id);
	}
	
	@Override
	public List<NutritionalRatio> getGlobalRatios() {
		return nutritionalRatioRepository.findByGlobal(true);
	}

	@Override
	public List<NutritionalRatio> getUserRatios(User user) {
		return nutritionalRatioRepository.findByCreator(user);

	}
	
}
