package com.calmeter.core.diary.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.model.DiaryEntry;

public interface IDiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {

	Optional<List<DiaryEntry>> findByUserAndDateTimeBetween(User user, LocalDateTime start, LocalDateTime end);
	
	Optional<List<DiaryEntry>> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
