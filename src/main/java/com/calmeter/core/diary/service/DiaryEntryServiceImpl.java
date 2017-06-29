package com.calmeter.core.diary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.repository.IDiaryEntryRepository;

@Component("diaryEntryService")
@Transactional
public class DiaryEntryServiceImpl
		implements IDiaryEntryService {

	@Autowired
	IDiaryEntryRepository diaryEntryRepository;

	@Override
	public DiaryEntry add (DiaryEntry diaryEntry) {
		return diaryEntryRepository.save (diaryEntry);
	}

}
