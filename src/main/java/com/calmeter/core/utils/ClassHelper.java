package com.calmeter.core.utils;

import com.google.common.collect.Sets;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Stream;

public class ClassHelper {

    public static Map<String, Object> beanProperties(Object bean) {
        try {
            Map<String, Object> map = new HashMap<>();
            Stream.of(Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors())
                    .filter(pd -> Objects.nonNull(pd.getReadMethod())).forEach(pd -> {
                try {
                    Object value = pd.getReadMethod().invoke(bean);
                    if (value != null) {
                        map.put(pd.getName(), value);
                    }
                } catch (Exception e) {
                    // add proper error handling here
                }
            });
            return map;
        } catch (IntrospectionException e) {
            return Collections.emptyMap();
        }
    }


    public static void addObjects(Object individual, Object total) {

        BeanWrapper individualAccessor = PropertyAccessorFactory.forBeanPropertyAccess(individual);
        BeanWrapper totalAccessor = PropertyAccessorFactory.forBeanPropertyAccess(total);

        for (PropertyDescriptor propertyDescriptor : individualAccessor.getPropertyDescriptors()) {

            String propertyDescriptorName = propertyDescriptor.getName();

            if (EXCLUSION_PROPERTIES.contains(propertyDescriptorName))
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
                    totalAccessor.setPropertyValue(propertyDescriptor.getName(), individualValue);
                    continue;
                }

                int value = (int) totalValue + (int) individualValue;
                totalAccessor.setPropertyValue(propertyDescriptorName, value);
                continue;
            }

            if (propertyDescriptor.getPropertyType().equals(double.class)
                    || propertyDescriptor.getPropertyType().equals(Double.class)) {

                if (totalValue == null) {
                    totalAccessor.setPropertyValue(propertyDescriptorName, individualValue);
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

                    Double sumValue;
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

    private static final Set<String> EXCLUSION_PROPERTIES = Sets.newHashSet("hibernateLazyInitializer",
            "annotatedInterfaces", "class", "annotatedSuperclass", "annotation", "annotations", "handler",
            "currentSession", "directlyAccessible", "dirty", "key", "owner", "role", "rowUpdatePossible", "session",
            "storedSnapshot", "unreferenced", "value", "infinite", "naN", "empty", "id", "modifiers");


}
