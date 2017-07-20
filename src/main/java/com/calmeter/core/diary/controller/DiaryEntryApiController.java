package com.calmeter.core.diary.controller;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.service.IDiaryEntryService;

@RestController
@RequestMapping("/api/diary")
public class DiaryEntryApiController {

	@Autowired
	UserHelper userHelper;

	@Autowired
	IDiaryEntryService diaryEntryService;

	@RequestMapping(value = "/getEntriesByDate", method = RequestMethod.GET)
	ResponseEntity<Collection<DiaryEntry>> getEntriesByDate(@RequestParam("date") String dateString) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);

		Optional<List<DiaryEntry>> entriesWrapper = diaryEntryService.getDiaryEntriesByDay(userWrapper.get(),
				zonedDateTime.toLocalDate());
		if (!entriesWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<DiaryEntry> entries = entriesWrapper.get();

		for (int i = 0; i < entries.size(); i++) {
			entries.get(i).computeNutritionalInformation();
		}

		if (entries.size() < 1) {
			return new ResponseEntity<Collection<DiaryEntry>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<DiaryEntry>>(entries, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTotalDiaryNutritionByDate", method = RequestMethod.GET)
	ResponseEntity<Collection<DiaryEntry>> getTotalDiaryNutritionByDate(@RequestParam("date") String dateString) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);

		Optional<List<DiaryEntry>> entriesWrapper = diaryEntryService.getDiaryEntriesByDay(userWrapper.get(),
				zonedDateTime.toLocalDate());
		if (!entriesWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<DiaryEntry> entries = entriesWrapper.get();

		for (int i = 0; i < entries.size(); i++) {
			entries.get(i).computeNutritionalInformation();
		}

		if (entries.size() < 1) {
			return new ResponseEntity<Collection<DiaryEntry>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<DiaryEntry>>(entries, HttpStatus.OK);
	}

	@RequestMapping(value = "/createEntry", method = RequestMethod.POST)
	ResponseEntity<?> createFoodItem(@RequestBody DiaryEntry diaryEntry) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		diaryEntry.setUser(userWrapper.get());
		DiaryEntry createdEntry = diaryEntryService.add(diaryEntry);
		return new ResponseEntity<String>("Created entry: " + createdEntry.getId(), HttpStatus.CREATED);

	}

	public static final Logger logger = LoggerFactory.getLogger(DiaryEntryApiController.class);

}
