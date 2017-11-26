package com.calmeter.core.food.utils;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.calmeter.core.Constants;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.utils.DoubleHelper;

public class FoodItemHelper {

	public static void applyServingModifierToValues(FoodItemEntry foodItemEntry) {

		FoodItem foodItem = SerializationUtils.clone(foodItemEntry.getFoodItemReference());

		Double weightInGrams = foodItemEntry.getWeightInGrams();
		Double modifier = weightInGrams / 100;

		BeanWrapper individualAccessor = PropertyAccessorFactory
				.forBeanPropertyAccess(foodItem.getNutritionalInformation());
		for (PropertyDescriptor propertyDescriptor : individualAccessor.getPropertyDescriptors()) {

			if (Constants.EXLUSION_PROPERTIES.contains(propertyDescriptor.getName()))
				continue;

			Object individualValue = individualAccessor.getPropertyValue(propertyDescriptor.getName());

			if (individualValue == null)
				continue;

			if (individualValue.getClass().equals(String.class))
				continue;

			if (propertyDescriptor.getPropertyType().equals(Integer.class)
					|| propertyDescriptor.getPropertyType().equals(int.class)) {

				Double updateValue = modifier * (Integer) individualValue;
				individualAccessor.setPropertyValue(propertyDescriptor.getName(),
						DoubleHelper.round(updateValue, 2).intValue());
				continue;
			}

			if (propertyDescriptor.getPropertyType().equals(double.class)
					|| propertyDescriptor.getPropertyType().equals(Double.class)) {

				Double updateValue = modifier * (double) individualValue;
				individualAccessor.setPropertyValue(propertyDescriptor.getName(), DoubleHelper.round(updateValue, 2));
				continue;
			}

			if (propertyDescriptor.getPropertyType().equals(Map.class)) {

				@SuppressWarnings("unchecked")
				Map<Object, Double> individualMap = (Map<Object, Double>) individualValue;

				for (Map.Entry<?, Double> entry : individualMap.entrySet()) {

					if (entry.getValue() == null)
						continue;

					Double rowValue = individualMap.get(entry.getKey());
					Double updateValue = modifier * (double) rowValue;

					individualMap.put(entry.getKey(), DoubleHelper.round(updateValue.intValue(), 2));
				}
				individualAccessor.setPropertyValue(propertyDescriptor.getName(), individualMap);
				continue;

			}
		}

		foodItemEntry.setComputedNutritionalInformation(foodItem.getNutritionalInformation());

	}

	public static final Logger logger = LoggerFactory.getLogger(FoodItemHelper.class);

}
