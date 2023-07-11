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
 * @author Md. Emran Hossain
 * @since 20 Jan, 2022
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "form_setups")
public class FormSetup extends AuditModel<Long> {

    private static final long serialVersionUID = 5169523883513848576L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baseType;
    private Long categoryId;
    private Boolean hasFieldVerification;
    private Boolean hasLabTest;
    private Boolean hasApplicationPayment;
    private Boolean hasProcessPayment;
    private boolean isActive;
}
