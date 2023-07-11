package com.synesis.mofl.lnm.payload;

import lombok.Data;

/**
 * @author Md. Kamruzzaman
 * @since 25 March, 2022
 * @version 1.1
 */
@Data
public class ApplicationFeeForLicenseNocResponse {
    String uid;
    Long applicantId;
    String applicationStatus;
    String categoryName;
    Long applicationFee;
    Long verificationFee;
    Long certificateFee;
    Boolean hasapplicationFee;
    Boolean hasverificationFee;
    Boolean hasCertificateFee;
    String applicantName;
    String applicantEmailAddress;
    String applicantMobileNo;
    Boolean userStatus;
    String address;
}
