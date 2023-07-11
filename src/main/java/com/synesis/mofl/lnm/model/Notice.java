package com.synesis.mofl.lnm.model;

import java.time.LocalDate;

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
 * @since 31 Jan, 2022
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notices")
public class Notice extends AuditModel<Long> {

    private static final long serialVersionUID = -1752039139217698299L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String baseType;

    @Nullable
    private Long departmentId;
    private String noticeTitle;
    private String noticeDetails;

    @Nullable
    private LocalDate fromDate;

    @Nullable
    private LocalDate toDate;

    @Nullable
    private String attachment;
    private Boolean isActive;
}
