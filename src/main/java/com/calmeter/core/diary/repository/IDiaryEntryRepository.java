package com.calmeter.core.diary.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.model.DiaryEntry;

public interface IDiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
	
	Optional<DiaryEntry> getById(long id);
	
	List<DiaryEntry> findByUser(User user);

	List<DiaryEntry> findByUserAndDateTimeBetween(User user, LocalDateTime start, LocalDateTime end);
	
	List<DiaryEntry> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
