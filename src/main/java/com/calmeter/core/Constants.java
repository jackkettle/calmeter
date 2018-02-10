package com.calmeter.core;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

public class Constants {

    public static final String USER_TASK_SET_PROFILE_DESCRIPTION = "Update your user profile: Set your height and weight";
    public static final String USER_TASK_SET_PROFILE_LOCATION = "/user/edit";
    public static final String USER_TASK_SET_PROFILE_ICON = "fa fa-2x fa-user-circle";

    public static final String USER_TASK_SET_GOALS_DESCRIPTION = "Update your goals: Set your weight saving plans";
    public static final String USER_TASK_SET_GOALS_LOCATION = "/goals";
    public static final String USER_TASK_SET_GOALS_ICON = "fa fa-2x fa-star-half-o";

    public static final Double KJ_IN_KCAL = 4.184;

    public static final Set<String> EXLUSION_PROPERTIES = Collections.unmodifiableSet(Sets.newHashSet("annotatedInterfaces", "class",
            "annotatedSuperclass", "annotation", "annotations", "currentSession", "directlyAccessible", "dirty", "key", "owner", "role",
            "rowUpdatePossible", "session", "storedSnapshot", "unreferenced", "value", "infinite", "naN", "empty", "id"));


}
