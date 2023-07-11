package com.synesis.mofl.lnm.helper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is for generate application id for stp registration
 *
 * @author Md. Emran Hossain
 * @since 24 Feb, 2021
 */
public class UIDGenerate {

    private static final Logger LOG = LoggerFactory.getLogger(UIDGenerate.class);

    /**
     * This Method is generate UId
     *
     * @author Md. Emran Hossain
     * @param baseTypePerfix - String
     * @return UID -String
     * @version 1.1
     * @since 24 Feb, 2021
     */
    public static String generateUId(String baseTypePerfix) {
        LocalDateTime currentDateTime = DateHelper.currentDateTime();

        String UID = baseTypePerfix +
                    currentDateTime.getYear() +
                    addOneZero(currentDateTime.getMonthValue()) +
                    addOneZero(currentDateTime.getDayOfMonth()) + 
                    addOneZero(currentDateTime.getHour()) + 
                    addOneZero(currentDateTime.getMinute()) + 
                    addOneZero(currentDateTime.getSecond()) + 
                    addTwoZero(currentDateTime.get(ChronoField.MILLI_OF_SECOND));

        LOG.info("Generated UID {}", UID);
        return UID;
    }

    /**
     * This Method add 1 zero in front of a number
     *
     * @author Md. Emran Hossain
     * @param number - int
     * @return String - String
     * @version 1.1
     * @since 24 Feb, 2022
     */
    public static String addOneZero(int number) {
        return String.format("%02d" , number);
    }

    /**
     * This Method add 2 zero in front of a number
     *
     * @author Md. Emran Hossain
     * @param number - int
     * @return String - String
     * @version 1.1
     * @since 24 Feb, 2022
     */
    public static String addTwoZero(int number) {
        return String.format("%03d" , number);
    }
}
