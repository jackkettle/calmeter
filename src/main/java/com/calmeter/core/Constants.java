package com.calmeter.core;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

public class Constants {

	public static final Double KJ_IN_KCAL = 4.184;
	
	public static final Set<String> EXLUSION_PROPERTIES = Collections.unmodifiableSet (Sets.newHashSet ("annotatedInterfaces", "class",
			"annotatedSuperclass", "annotation", "annotations", "currentSession", "directlyAccessible", "dirty", "key", "owner", "role",
			"rowUpdatePossible", "session", "storedSnapshot", "unreferenced", "value", "infinite", "naN", "empty", "id"));


}
