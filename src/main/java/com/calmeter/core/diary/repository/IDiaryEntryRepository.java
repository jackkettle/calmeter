package com.calmeter.core.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.diary.model.DiaryEntry;

public interface IDiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
}
