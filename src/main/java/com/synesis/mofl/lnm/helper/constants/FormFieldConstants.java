package com.synesis.mofl.lnm.helper.constants;
/**
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public class FormFieldConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/form-field";
    public static final String GET_ALL = "/form-fields";
    public static final String GET_BY_ID = "/form-field/{id}";
    public static final String GET_BY_FORM_SETUP_ID = "/form-field-by-form-setup/{id}";
    public static final String SAVE = "/save-form-field";
    public static final String UPDATE = "/update-form-field";
    public static final String DELETE = "/delete-form-field/{id}";
    public static final String DELETE_WITH_VALIDATION = "/delete-form-field";
    public static final String GET_BY_TYPE_AND_CAT_ID = "/form-field-by-type-catid";
}