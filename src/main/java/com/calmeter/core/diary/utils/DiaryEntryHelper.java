package com.calmeter.core.diary.utils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.utils.DoubleHelper;
import com.google.common.collect.Sets;

@Component
public class DiaryEntryHelper {

	public static NutritionalInformation computeNutritionalInformation(Collection<DiaryEntry> diaryEntryList) {
		NutritionalInformation totalNutritionalInformation = new NutritionalInformation();

		for (DiaryEntry diaryEntry : diaryEntryList) {
			diaryEntry.applyServingsModifiers();
			diaryEntry.computeNutritionalInformation();

			addObjects(diaryEntry.getTotalNutrionalnformation(), totalNutritionalInformation);
		}
		return totalNutritionalInformation;
	}

	public static NutritionalInformation computeNutritionalInformation(DiaryEntry diaryEntry) {
		NutritionalInformation totalNutritionalInformation = new NutritionalInformation();

		for (FoodItemEntry foodItemEntry : diaryEntry.getFoodItemEntries()) {
			addObjects(foodItemEntry.getComputedNutritionalInformation(), totalNutritionalInformation);
		}

		return totalNutritionalInformation;
	}

	public static void addObjects(Object individual, Object total) {

		BeanWrapper individualAccessor = PropertyAccessorFactory.forBeanPropertyAccess(individual);
		BeanWrapper totalAccessor = PropertyAccessorFactory.forBeanPropertyAccess(total);

		for (PropertyDescriptor propertyDescriptor : individualAccessor.getPropertyDescriptors()) {

			String propertyDescriptorName = propertyDescriptor.getName();

			if (EXLUSION_PROPERTIES.contains(propertyDescriptorName))
				continue;

			Object individualValue = individualAccessor.getPropertyValue(propertyDescriptorName);
			Object totalValue = totalAccessor.getPropertyValue(propertyDescriptorName);

			if (individualValue == null)
				continue;

			if (individualValue.getClass().equals(String.class))
				continue;

			if (propertyDescriptor.getPropertyType().equals(Integer.class)
					|| propertyDescriptor.getPropertyType().equals(int.class)) {

				if (totalValue == null) {
					totalAccessor.setPropertyValue(propertyDescriptor.getName(), (int) individualValue);
					continue;
				}

				int value = (int) totalValue + (int) individualValue;
				totalAccessor.setPropertyValue(propertyDescriptorName, value);
				continue;
			}

			if (propertyDescriptor.getPropertyType().equals(double.class)
					|| propertyDescriptor.getPropertyType().equals(Double.class)) {

				if (totalValue == null) {
					totalAccessor.setPropertyValue(propertyDescriptorName, (double) individualValue);
					continue;
				}

				double value = (double) totalValue + (double) individualValue;
				totalAccessor.setPropertyValue(propertyDescriptorName, DoubleHelper.round(value, 2));
				continue;
			}

			if (propertyDescriptor.getPropertyType().equals(Map.class)) {

				@SuppressWarnings("unchecked")
				Map<Object, Double> individualMap = (Map<Object, Double>) individualValue;
				if (totalValue == null) {
					totalValue = new HashMap<Object, Double>();
				}

				@SuppressWarnings("unchecked")
				Map<Object, Double> totalMap = (Map<Object, Double>) totalValue;
				for (Map.Entry<?, Double> entry : individualMap.entrySet()) {

					if (entry.getValue() == null)
						continue;

					Double rowTotalValue = totalMap.get(entry.getKey());

					Double sumValue = 0.0;
					if (rowTotalValue == null)
						sumValue = entry.getValue();
					else
						sumValue = entry.getValue() + rowTotalValue;

					totalMap.put(entry.getKey(), DoubleHelper.round(sumValue, 2));
				}
				totalAccessor.setPropertyValue(propertyDescriptorName, totalMap);
				continue;

			}

			addObjects(individualValue, totalValue);
		}

	}

	public static final Set<String> EXLUSION_PROPERTIES = Sets.newHashSet("hibernateLazyInitializer",
			"annotatedInterfaces", "class", "annotatedSuperclass", "annotation", "annotations", "handler",
			"currentSession", "directlyAccessible", "dirty", "key", "owner", "role", "rowUpdatePossible", "session",
			"storedSnapshot", "unreferenced", "value", "infinite", "naN", "empty", "id", "modifiers");

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DiaryEntryHelper.class);

}
