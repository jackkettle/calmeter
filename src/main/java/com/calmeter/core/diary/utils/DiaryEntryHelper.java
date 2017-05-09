package com.calmeter.core.diary.utils;

import java.beans.PropertyDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;

public class DiaryEntryHelper {

	public static NutritionalInformation computeNutritionalInformation (DiaryEntry diaryEntry) {
		NutritionalInformation totalNutritionalInformation = new NutritionalInformation ();

		for (FoodItem foodItem : diaryEntry.getFoodItems ()) {
			addObjects (foodItem.getNutritionalInformation (), totalNutritionalInformation);
		}

		return totalNutritionalInformation;
	}

	public static void addObjects (Object individual, Object total) {

		BeanWrapper individualAccessor = PropertyAccessorFactory.forBeanPropertyAccess (individual);
		BeanWrapper totalAccessor = PropertyAccessorFactory.forBeanPropertyAccess (total);

		for (PropertyDescriptor propertyDescriptor : individualAccessor.getPropertyDescriptors ()) {
			Object individualValue = individualAccessor.getPropertyValue (propertyDescriptor.getName ());
			Object totalValue = totalAccessor.getPropertyValue (propertyDescriptor.getName ());

			logger.info ("Adding element: {}", propertyDescriptor.getName ());
			logger.info ("Adding element: {}", propertyDescriptor.getPropertyType ());

			if (individualValue.getClass ().equals (double.class)) {
				double value = (double)totalValue + (double)individualValue;
				totalAccessor.setPropertyValue (propertyDescriptor.getName (), value);
				continue;
			}

			addObjects (individualValue, totalValue);
		}

	}

	private static Logger logger = LoggerFactory.getLogger (DiaryEntryHelper.class);

}
