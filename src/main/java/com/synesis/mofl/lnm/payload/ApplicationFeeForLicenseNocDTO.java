package com.synesis.mofl.lnm.payload;

/**
 * @author Md. Kamruzzaman
 * @since 23 March, 2022
 * @version 1.1
 */
public interface ApplicationFeeForLicenseNocDTO {
    String getUid();
    Long getApplicantId();
    String getApplicationStatus();
    String getCategoryName();
    Long getapplicationFee();
    Long getverificationFee();
    Long getcertificateFee();
    Boolean gethasapplicationFee();
    Boolean gethasverificationFee();
    Boolean gethasCertificateFee();



}
