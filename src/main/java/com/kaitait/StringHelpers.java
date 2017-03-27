package com.kaitait;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kai-tait on 27/03/2017.
 */
public class StringHelpers {
    /**
     * Creates a camel case setter for a camel case or snake case variable passed in.
     * eg. if the variable is objectId the setter returned will be setObjectId
     * eg. if the variable is object_id the setter returned will be setObjectId
     *
     * @param variableName the variable name as a string
     * @return a String setter name in camel case
     */
    protected static String getSetterFor(String variableName) {
        if (isSnakeCase(variableName)) {
            variableName = fromSnakeCase(variableName);
        }
        return "set" + capitalizeFirstLetter(variableName);
    }

    /**
     * Returns the String passed in with the first letter capitalised.
     *
     * @param variableName the variable name as a string
     * @return a String with the first letter in uppercase
     */
    private static String capitalizeFirstLetter(final String variableName) {
        return variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
    }

    /**
     * Assumes if there's an _ in the variable name it's in snake case.
     * @param variableName
     * @return
     */
    private static boolean isSnakeCase(final String variableName) {
        return variableName.contains("_");
    }

    /**
     * Convert from snake case to camel case
     * @param value the string to convert
     * @return a camel case string
     */
    public static String fromSnakeCase(final String value) {
        StringBuffer stringBuffer = new StringBuffer();

        String[] words = value.split("_");
        for (String i : words) {
            stringBuffer.append(StringUtils.capitalize(i));
        }

        String variableName = stringBuffer.toString();
        return variableName.substring(0, 1).toLowerCase() + variableName.substring(1);
    }
}
