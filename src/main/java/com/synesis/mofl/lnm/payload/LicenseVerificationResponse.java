package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is for custom response for LicenseVerification
 * 
 * @author Md Mushfiq Fuad
 * @since 24 Mar, 2022
 *
 */
@Getter
@Setter
public class LicenseVerificationResponse {
    private Long id;
    private String verificationType;
    private String reportAttachment;
    private String documentAttachment;
    private LicenseApplicationResponse licenseApplicationResponse;
    private String verifiedBy;
    private String remarks;
}
