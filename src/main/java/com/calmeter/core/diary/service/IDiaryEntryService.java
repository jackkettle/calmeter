package com.calmeter.core.diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.model.DiaryEntry;

public interface IDiaryEntryService {

	DiaryEntry add (DiaryEntry diaryEntry);

	Optional<List<DiaryEntry>> getDiaryEntriesByDay(User user, LocalDate date);
	
	Optional<List<DiaryEntry>> getAllDiaryEntriesByDay(LocalDate date);
	
}
