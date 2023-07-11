package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 02 Feb, 2022
 * @version 1.1
 */
@Getter
@Setter
public class LicenseVerificationPayload {

    private Long id;
    private String verificationType;
    private String reportAttachment;
    private String documentAttachment;
    private Long licenseApplicationId;
    private Long verifiedBy;
    private String remarks;
}
