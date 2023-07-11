package com.synesis.mofl.lnm.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Getter
@Setter
public class CertificatePayload {

    private Long id;
    private Long userId;
    private LocalDateTime issueDate;
    private Long issuedBy;
    private String certificateType;

    private Long applicationId;
    private String certificateName;
    private String description;
    private LocalDate expireDate;
    private LocalDate renewalDate;

    private LocalDate rejectionDate;
    private Boolean isActive;
    private Boolean isArchived;
    private String remarks;
    private String uid;
}
