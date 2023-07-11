package com.synesis.mofl.lnm.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Emran Hossain
 * @since 21 Mar, 2022
 *
 */
@Getter
@Setter
public class CertificateResponse {

    private Long id;
    private Long userId;
    private LocalDateTime issueDate;
    private Long issuedBy;
    private String certificateType;
    private LicenseApplicationResponse licenseApplicationResponse;
    private NocApplicationResponse nocApplicationResponse;
    private String certificateName;
    private String description;
    private LocalDate expireDate;
    private LocalDate renewalDate;
    private LocalDate rejectionDate;
    private Boolean isActive;
    private Boolean isArchived;
    private String remarks;
}
