package com.synesis.mofl.lnm.helper.constants;
/**
 * @author Md. Emran Hossain
 * @since 25 Feb, 2022
 * @version 1.1
 */
public class CertificateConstants {

    public static final String CONTEXT_PATH = "/api";
    public static final String ROOT = CONTEXT_PATH + "/certificate";
    public static final String GET_ALL = "/certificates";
    public static final String GET_BY_ID = "/certificate/{id}";
    public static final String GET_ALL_BY_TYPE = "/certificates-by-type";
    public static final String GET_ALL_ACTIVE_CERTIFICATE = "/active-certificates";
    public static final String GET_BY_USER_ID = "/user-wise-certificates";
    public static final String SAVE = "/save-certificate";
    public static final String UPDATE = "/update-certificate";
    public static final String FORCE_DEACTIVATE = "/force-deactive-license";
    public static final String RENEW = "/renew-certificate";
}