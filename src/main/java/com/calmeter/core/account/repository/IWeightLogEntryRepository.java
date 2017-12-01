package com.calmeter.core.account.repository;

import com.calmeter.core.account.model.WeightLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWeightLogEntryRepository extends JpaRepository<WeightLogEntry, Long> {

}
