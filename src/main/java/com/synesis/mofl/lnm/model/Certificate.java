package com.synesis.mofl.lnm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * @author Md. Emran Hossain
 * @since 22 Jan, 2022
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "certificates")
public class Certificate extends AuditModel<Long> {

    private static final long serialVersionUID = -2582604770753022623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDateTime issueDate;
    private Long issuedBy;
    private String certificateType;
    private Long applicationId;
    private String certificateName;

    @Nullable
    private String description;

    @Nullable
    private LocalDate expireDate;

    @Nullable
    private LocalDate renewalDate;

    @Nullable
    private LocalDate rejectionDate;
    private Boolean isActive;
    private Boolean isArchived;
    private String remarks;
}
