package com.yl.seckill.utils;

import java.util.regex.Pattern;

/**
 * @author Administrator
 */
public final class RegexUtil {
    private RegexUtil() {
    }

    public static final Pattern UPPER_REGEX = Pattern.compile("[A-Z]+");

    public static final Pattern LOWER_REGEX = Pattern.compile("[a-z]+");

    public static final Pattern NUMBER_REGEX = Pattern.compile("[0-9]+");

    public static final Pattern SPECIAL_REGEX = Pattern.compile("[$()*+.\\[\\]?!@#%&~_-]+");
}