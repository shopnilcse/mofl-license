package com.synesis.mofl.lnm.helper.constants;
/**
 * @author Md. Emran Hossain
 * @since 25 Feb, 2022
 * @version 1.1
 */
public class LicenseVerificationConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/license-verification";
    public static final String GET_BY_APP_ID = "/license-verification/application-id/";
    public static final String GET_BY_USER_ID = "/license-verification/user-id/";
    public static final String GET_BY_ID = "/license-verification/{id}";
    public static final String SAVE = "/save-license-verification";
    public static final String UPDATE = "/update-license-verification";
}