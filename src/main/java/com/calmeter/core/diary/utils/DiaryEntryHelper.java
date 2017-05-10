package com.calmeter.core.diary.utils;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.google.common.collect.Sets;

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

			if (EXLUSION_PROPERTIES.contains (propertyDescriptor.getName ()))
				continue;

			Object individualValue = individualAccessor.getPropertyValue (propertyDescriptor.getName ());
			Object totalValue = totalAccessor.getPropertyValue (propertyDescriptor.getName ());

			if (individualValue == null)
				continue;

			if (individualValue.getClass ().equals (String.class))
				continue;

			if (propertyDescriptor.getPropertyType ().equals (Integer.class) || propertyDescriptor.getPropertyType ().equals (int.class)) {

				if (totalValue == null) {
					totalAccessor.setPropertyValue (propertyDescriptor.getName (), (int)individualValue);
					continue;
				}

				int value = (int)totalValue + (int)individualValue;
				totalAccessor.setPropertyValue (propertyDescriptor.getName (), value);
				continue;
			}

			if (propertyDescriptor.getPropertyType ().equals (double.class) || propertyDescriptor.getPropertyType ().equals (Double.class)) {

				if (totalValue == null) {
					totalAccessor.setPropertyValue (propertyDescriptor.getName (), (double)individualValue);
					continue;
				}

				double value = (double)totalValue + (double)individualValue;
				totalAccessor.setPropertyValue (propertyDescriptor.getName (), value);
				continue;
			}

			if (propertyDescriptor.getPropertyType ().equals (Map.class)) {

				@SuppressWarnings("unchecked")
				Map<Object, Double> individualMap = (Map<Object, Double>)individualValue;
				if (totalValue == null) {
					totalAccessor.setPropertyValue (propertyDescriptor.getName (), individualMap);
					continue;
				}

				@SuppressWarnings("unchecked")
				Map<Object, Double> totalMap = (Map<Object, Double>)totalValue;
				for (Map.Entry<?, Double> entry : individualMap.entrySet ()) {

					if (entry.getValue () == null)
						continue;

					Double rowTotalValue = totalMap.get (entry.getKey ());

					Double sumValue = 0.0;
					if (rowTotalValue == null)
						sumValue = entry.getValue ();
					else
						sumValue = entry.getValue () + rowTotalValue;

					totalMap.put (entry.getKey (), sumValue);
				}
				totalAccessor.setPropertyValue (propertyDescriptor.getName (), totalMap);
				continue;

			}

			addObjects (individualValue, totalValue);
		}

	}

	public static final Set<String> EXLUSION_PROPERTIES = Collections.unmodifiableSet (Sets.newHashSet ("annotatedInterfaces", "class",
			"annotatedSuperclass", "annotation", "annotations", "currentSession", "directlyAccessible", "dirty", "key", "owner", "role",
			"rowUpdatePossible", "session", "storedSnapshot", "unreferenced", "value", "infinite", "naN", "empty", "id"));

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger (DiaryEntryHelper.class);

}
