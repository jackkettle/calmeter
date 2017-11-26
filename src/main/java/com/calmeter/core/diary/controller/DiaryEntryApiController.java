package com.calmeter.core.diary.controller;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.service.IDiaryEntryService;
import com.calmeter.core.diary.utils.DiaryEntryHelper;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.service.IFoodItemEntryService;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.service.INutritionalInformationService;

@RestController
@RequestMapping("/api/diary")
public class DiaryEntryApiController {

	@Autowired
	UserHelper userHelper;

	@Autowired
	IDiaryEntryService diaryEntryService;

	@Autowired
	IFoodItemService foodItemService;

	@Autowired
	IFoodItemEntryService foodItemEntryService;

	@Autowired
	INutritionalInformationService nutritionalInformationService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	ResponseEntity<DiaryEntry> get(@RequestParam("id") long id) {

		Optional<DiaryEntry> entryWrapper = diaryEntryService.get(id);
		if (!entryWrapper.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		if (!(entryWrapper.get().getUser().getId().equals(userWrapper.get().getId())
				|| userHelper.isAdmin(userWrapper.get())))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		return new ResponseEntity<>(entryWrapper.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getLast7Days", method = RequestMethod.GET)
	ResponseEntity<Map<LocalDate, NutritionalInformation>> getLast7Days(@RequestParam("date") String dateString) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);
		LocalDate day = zonedDateTime.toLocalDate();

		Map<LocalDate, NutritionalInformation> days = new HashMap<>();

		for (int i = 0; i < 7; i++) {

			List<DiaryEntry> entries = diaryEntryService.getDiaryEntriesByDay(userWrapper.get(), day);

			if (entries.size() < 1) {
				days.put(day, new NutritionalInformation());
				day = day.minusDays(1);
				continue;
			}

			NutritionalInformation nutritionalInformation = DiaryEntryHelper.computeNutritionalInformation(entries);
			days.put(day, nutritionalInformation);
			day = day.minusDays(1);
		}

		return new ResponseEntity<>(days, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTotalDiaryNutritionByDate", method = RequestMethod.GET)
	ResponseEntity<NutritionalInformation> getTotalDiaryNutritionByDate(@RequestParam("date") String dateString) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);

		List<DiaryEntry> entries = diaryEntryService.getDiaryEntriesByDay(userWrapper.get(),
				zonedDateTime.toLocalDate());

		if (entries.size() < 1) {
			return new ResponseEntity<NutritionalInformation>(new NutritionalInformation(), HttpStatus.OK);
		}

		NutritionalInformation nutritionalInformation = DiaryEntryHelper.computeNutritionalInformation(entries);
		return new ResponseEntity<>(nutritionalInformation, HttpStatus.OK);

	}

	@RequestMapping(value = "/getEntriesByDate", method = RequestMethod.GET)
	ResponseEntity<Collection<DiaryEntry>> getEntriesByDate(@RequestParam("date") String dateString) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);

		List<DiaryEntry> entries = diaryEntryService.getDiaryEntriesByDay(userWrapper.get(),
				zonedDateTime.toLocalDate());

		if (entries.size() < 1) {
			return new ResponseEntity<Collection<DiaryEntry>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<DiaryEntry>>(entries, HttpStatus.OK);
	}

	@RequestMapping(value = "/createEntry", method = RequestMethod.POST)
	ResponseEntity<?> createEntry(@RequestBody DiaryEntry diaryEntry) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		for (FoodItemEntry entry : diaryEntry.getFoodItemEntries()) {

			if (entry.getFoodItemReference().getFoodItemType().equals(FoodItemType.USER_ITEM))
				continue;

			if (!foodItemService.existsExternal(entry.getFoodItemReference().getExternalId(),
					entry.getFoodItemReference().getFoodItemType())) {

				logger.info("Saving foodItemEntry to db. FoodItem: {}", entry.getFoodItemReference().getId());
				foodItemService.save(entry.getFoodItemReference());
				logger.info("FoodItemEntry Id: {}", entry.getFoodItemReference().getId());
			}

		}

		diaryEntry.applyServingsModifiers();
		diaryEntry.computeNutritionalInformation();
		diaryEntry.setUser(userWrapper.get());

		diaryEntryService.save(diaryEntry);

		for (FoodItemEntry entry : diaryEntry.getFoodItemEntries()) {
			foodItemEntryService.save(entry);
		}

		return new ResponseEntity<String>("Created entry: " + diaryEntry.getId(), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/deleteEntry/{id}", method = RequestMethod.DELETE)
	ResponseEntity<String> delete(@PathVariable("id") long id) {

		logger.info("Delete diary entry called: {}", id);
		Optional<DiaryEntry> entryWrapper = diaryEntryService.get(id);
		if (!entryWrapper.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		if (!(entryWrapper.get().getUser().getId().equals(userWrapper.get().getId())
				|| userHelper.isAdmin(userWrapper.get())))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		diaryEntryService.delete(entryWrapper.get());
		return new ResponseEntity<String>("Deleted entry: " + id, HttpStatus.OK);
	}

	public static final Logger logger = LoggerFactory.getLogger(DiaryEntryApiController.class);

}
