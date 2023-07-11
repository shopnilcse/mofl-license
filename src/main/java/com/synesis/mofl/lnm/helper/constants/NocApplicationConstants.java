package com.synesis.mofl.lnm.helper.constants;

/**
 * @author Md. Mushfiq Fuad
 * @since 24 Feb, 2022
 * @version 1.1
 */
public class NocApplicationConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/noc-application";
    public static final String GET_ALL = "/noc-applications";
    public static final String GET_BY_ID = "/noc-application/{id}";
    public static final String GET_BY_APPLICANT_ID = "/noc-applicant-applications";
    public static final String SAVE = "/save-noc-application";
    public static final String UPDATE = "/update-noc-application";
    public static final String GET_ALL_APPROVED = "/approved-noc-applications";
    public static final String GET_ALL_LABTEST_NOC = "/noc-labtest-applications";
    public static final String COUNT_ALL_BY_TYPE = "/count-applications";
    public static final String GET_ALL_BY_CRITERIA = "/noc-report";
    public static final String GET_ALL_BY_DEPARTMENT_ID = "/noc-applications-department-wise";
    public static final String UPDATE_NOC_STATUS = "/update-noc-status";
}
