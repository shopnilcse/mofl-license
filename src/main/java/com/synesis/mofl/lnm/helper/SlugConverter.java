package com.synesis.mofl.lnm.helper;

/**
 * 
 * 
 * @author Md Mushfiq Fuad
 * @since 15 Mar, 2022
 *
 */
public class SlugConverter {

    /**
     * This method for convert string to slug
     * 
     * @author Md Mushfiq Fuad
     * @since 15 Mar, 2022
     * @param str - String
     * @return String - String
     */
    public static String slugConverter(String str) {
        String convertToSlug = str.toLowerCase().replaceAll("\\s+", "_");
        return convertToSlug;
    }
}
