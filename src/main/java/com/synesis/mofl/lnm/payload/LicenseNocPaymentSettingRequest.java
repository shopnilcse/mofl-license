package com.synesis.mofl.lnm.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 *
 */
@Getter
@Setter
public class LicenseNocPaymentSettingRequest {

    private Long id;
    @NotNull
    private String baseType;
    private Long categoryId;
    private Double applicationFee;
    private Double verificationFee;
    private Double certificateFee;
    private Boolean hasApplicationFee;
    private Boolean hasVerificationFee;
    private Boolean hasCertificateFee;
    private Boolean hasLabtestFee;
}
