package com.calmeter.core.diary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
	public DiaryEntry save(DiaryEntry diaryEntry) {
		return diaryEntryRepository.save(diaryEntry);
	}

	@Override
	public List<DiaryEntry> getDiaryEntriesByDay(User user, LocalDate day) {

		LocalDateTime start = day.atStartOfDay();
		LocalDateTime end = start.plusDays(1);

		return diaryEntryRepository.findByUserAndDateTimeBetween(user, start, end);
	}

	@Override
	public List<DiaryEntry> getAllDiaryEntriesByDay(LocalDate day) {
		LocalDateTime start = day.atStartOfDay();
		LocalDateTime end = start.plusDays(1);

		return diaryEntryRepository.findByDateTimeBetween(start, end);
	}

}
