package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 22 Feb, 2022
 *
 */
@Getter
@Setter
public class LicenseNocPaymentSettingResponse {

    private Long id;
    private String baseType;

    private Object category;
    private Double applicationFee;
    private Double verificationFee;
    private Double certificateFee;
    private Boolean hasApplicationFee;
    private Boolean hasVerificationFee;
    private Boolean hasCertificateFee;
    private Boolean hasLabtestFee;
}
