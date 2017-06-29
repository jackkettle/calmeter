package com.calmeter.core.diary.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.diary.model.DiaryEntry;

@Component("diaryEntryService")
@Transactional
public interface IDiaryEntryService {

	DiaryEntry add (DiaryEntry diaryEntry);

}
