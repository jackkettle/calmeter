package com.calmeter.core.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClassHelper {

	public static Map<String, Object> beanProperties (Object bean) {
		try {
			Map<String, Object> map = new HashMap<> ();
			Arrays.asList (Introspector.getBeanInfo (bean.getClass (), Object.class).getPropertyDescriptors ()).stream ()
					.filter (pd -> Objects.nonNull (pd.getReadMethod ())).forEach (pd -> {
						try {
							Object value = pd.getReadMethod ().invoke (bean);
							if (value != null) {
								map.put (pd.getName (), value);
							}
						}
						catch (Exception e) {
							// add proper error handling here
						}
					});
			return map;
		}
		catch (IntrospectionException e) {
			return Collections.emptyMap ();
		}
	}

}
