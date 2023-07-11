package com.synesis.mofl.lnm.helper.constants;
/**
 * @author Md. Emran Hossain
 * @since 22 Feb, 2022
 * @version 1.1
 */
public class LicenseApplicationConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/license-application";
    public static final String GET_ALL = "/license-applications";
    public static final String GET_BY_ID = "/license-application/{id}";
    public static final String GET_BY_APPLICANT_ID = "/license-applicant-applications";
    public static final String SAVE = "/save-license-application";
    public static final String GET_ALL_APPROVED = "/approved-license-applications";
    public static final String GET_ALL_ISSUED = "/issued-license-applications";
    public static final String GET_ALL_BY_DEPARTMENT_ID = "/license-applications-department-wise";
    public static final String COUNT_ALL_BY_TYPE = "/count-applications";
    public static final String GET_ALL_BY_CRITERIA = "/license-report";
    
    public static final String UPDATE_LICENSE_STATUS = "/update-license-status";
}