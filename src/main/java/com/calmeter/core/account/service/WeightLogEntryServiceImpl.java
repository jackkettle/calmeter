package com.calmeter.core.account.service;

import com.calmeter.core.account.model.WeightLogEntry;
import com.calmeter.core.account.repository.IWeightLogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("weightLogEntryService")
public class WeightLogEntryServiceImpl implements IWeightLogEntryService {

    private IWeightLogEntryRepository weightLogEntryRepository;

    @Autowired
    public WeightLogEntryServiceImpl(IWeightLogEntryRepository weightLogEntryRepository) {
        this.weightLogEntryRepository = weightLogEntryRepository;
    }

    @Override
    public WeightLogEntry save(WeightLogEntry weightLogEntry) {
        return weightLogEntryRepository.save(weightLogEntry);
    }

}
