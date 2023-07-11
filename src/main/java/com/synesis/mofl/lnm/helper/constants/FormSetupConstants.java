package com.synesis.mofl.lnm.helper.constants;

/**
 * This constant file contains all API end points for Form Setup
 * 
 * @author Md Mushfiq Fuad
 * @since 04 Apr, 2022
 */
public class FormSetupConstants {
    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/form-setup";
    public static final String GET_ALL = "/form-setups";
    public static final String GET_ALL_BY_TYPE = "/form-setups-type";
    public static final String GET_BY_ID = "/form-setup/{id}";
    public static final String SAVE = "/save-form-setup";
    public static final String UPDATE = "/update-form-setup";
    public static final String HAS_SETUP_EXIST = "/has-form-setup";
    public static final String UPDATE_ACTIVATION = "/activate-form-setup";
}
