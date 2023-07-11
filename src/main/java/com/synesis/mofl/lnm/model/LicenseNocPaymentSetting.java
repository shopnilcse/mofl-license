package com.synesis.mofl.lnm.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 23 Jan, 2022
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "license_noc_payment_settings")
public class LicenseNocPaymentSetting extends AuditModel<Long>{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String baseType;
    private Long categoryId;
    @Nullable
    private Double applicationFee;
    @Nullable
    private Double verificationFee;
    @Nullable
    private Double certificateFee;
    private Boolean hasApplicationFee;
    private Boolean hasVerificationFee;
    private Boolean hasLabtestFee;
    private Boolean hasCertificateFee;

}
