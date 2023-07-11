package com.synesis.mofl.lnm.helper.constants;

/**
 * @author Md Mushfiq Fuad
 * @since 01 Mar, 2022
 * @version 1.1
 */
public class LicenseNocPaymentSettingConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/payment-setting";
    public static final String GET_ALL = "/payment-settings";
    public static final String GET_ALL_BY_LICENSE = "/license-payment-settings";
    public static final String GET_ALL_BY_NOC = "/noc-payment-settings";
    public static final String GET_BY_ID = "/payment-setting/{id}";
    public static final String GET_DETAIL_BY_ID = "/payment-setting-details/{id}";
    public static final String SAVE = "/save-payment-setting";
    public static final String UPDATE = "/update-payment-setting";
    public static final String HAS_LICENSE_NOC_PAYMENT_SETTING = "/has-payment-setting";
}
