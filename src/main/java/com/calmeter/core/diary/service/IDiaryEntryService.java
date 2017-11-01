package com.calmeter.core.diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.model.DiaryEntry;

public interface IDiaryEntryService {

	Optional<DiaryEntry> get(long id);
	
	DiaryEntry save(DiaryEntry diaryEntry);

	List<DiaryEntry> getDiaryEntriesByDay(User user, LocalDate date);

	List<DiaryEntry> getAllDiaryEntriesByDay(LocalDate date);
	
	void delete(long id);

	void delete(DiaryEntry diaryEntry);

}
