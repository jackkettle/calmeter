package com.calmeter.core.diary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.repository.IDiaryEntryRepository;

@Component("diaryEntryService")
@Transactional
public class DiaryEntryServiceImpl implements IDiaryEntryService {

	@Autowired
	IDiaryEntryRepository diaryEntryRepository;

	@Override
	public DiaryEntry add(DiaryEntry diaryEntry) {
		return diaryEntryRepository.save(diaryEntry);
	}

	@Override
	public Optional<List<DiaryEntry>> getDiaryEntriesByDay(User user, LocalDate day) {

		LocalDateTime start = day.atStartOfDay();
		LocalDateTime end = start.plusDays(1);

		logger.info("Start: {}", start);
		logger.info("end: {}", end);
		logger.info("user: {}", user.getUsername());

		return diaryEntryRepository.findByUserAndDateTimeBetween(user, start, end);
	}

	private static Logger logger = LoggerFactory.getLogger(DiaryEntryServiceImpl.class);

	@Override
	public Optional<List<DiaryEntry>> getAllDiaryEntriesByDay(LocalDate day) {
		LocalDateTime start = day.atStartOfDay();
		LocalDateTime end = start.plusDays(1);

		logger.info("Start: {}", start);
		logger.info("end: {}", end);

		return diaryEntryRepository.findByDateTimeBetween(start, end);
	}

}
