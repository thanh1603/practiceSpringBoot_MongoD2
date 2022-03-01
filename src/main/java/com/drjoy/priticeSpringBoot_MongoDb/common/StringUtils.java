package com.drjoy.priticeSpringBoot_MongoDb.common;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class StringUtils {
    public static final String EMPTY = "";
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
