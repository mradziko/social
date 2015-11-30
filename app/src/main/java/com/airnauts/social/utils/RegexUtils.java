package com.airnauts.social.utils;

/**
 * Created by mradziko on 30.11.2015.
 */
public class RegexUtils {

    private static String USERNAME_PATTERN = "[a-zA-Z.]+";

    public static boolean isProperUsername(String username) {
        return username != null && username.length() > 3 && username.matches(USERNAME_PATTERN);
    }
}
