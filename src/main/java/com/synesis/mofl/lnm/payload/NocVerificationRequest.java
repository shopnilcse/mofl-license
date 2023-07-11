package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * This payload is for Noc Verification
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 *
 */
@Getter
@Setter
public class NocVerificationRequest {

    private Long id;
    private String verificationType;
    private String reportAttachment;
    private String documentAttachment;
    private String labTestReport;
    private Long nocApplicationId;
    private Long verifiedBy;
}
