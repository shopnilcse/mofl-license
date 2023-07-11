package com.synesis.mofl.lnm.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @author Md Emran Hossain
 * @since 14 Mar, 2022
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "license_noc_statuses")
public class LicenseNocStatus extends AuditModel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String statusSlug;
    private Boolean isSubmitted;

    private Boolean isApplicationApproved;
    private Boolean isApplicationFeePaid;
    private Boolean isVerified;
    private Boolean isVerificationFeePaid;
    private Boolean isLabTested;

    private Boolean isLabFeePaid;
    private Boolean isFinalApproved;
    private Boolean isAttachmentApproved;
    private Boolean isCertificateFeePaid;
    private Boolean isCertificateIssued;

    private Boolean isEnothiApproved;
    private Boolean isCertificateExpired;
    private Boolean isCertificateRenewed;
}
