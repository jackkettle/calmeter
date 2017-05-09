package com.calmeter.core.diary.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.repository.IDiaryEntryRepository;

@RestController
@RequestMapping("/api/diary")
public class DiaryApiController {

	@Autowired
	IDiaryEntryRepository diaryEntryRepository;

	@RequestMapping(value = "/allEntries", method = RequestMethod.GET)
	ResponseEntity<Collection<DiaryEntry>> getAllEntries () {
		List<DiaryEntry> entries = diaryEntryRepository.findAll ();

		for (int i = 0; i < entries.size (); i++) {
			entries.get (i).computeNutritionalInformation ();
		}

		if (entries.size () < 1) {
			return new ResponseEntity<Collection<DiaryEntry>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<DiaryEntry>> (entries, HttpStatus.OK);
	}

	@RequestMapping(value = "/createEntry", method = RequestMethod.POST)
	ResponseEntity<?> createFoodItem (@RequestBody DiaryEntry diaryEntry) {

		logger.info ("Creating diary entry : {}", diaryEntry);
		DiaryEntry createdEntry = diaryEntryRepository.save (diaryEntry);
		logger.info ("Diary entry created id: {}", createdEntry.getId ());
		return new ResponseEntity<String> (HttpStatus.CREATED);

	}

	public static final Logger logger = LoggerFactory.getLogger (DiaryApiController.class);

}
