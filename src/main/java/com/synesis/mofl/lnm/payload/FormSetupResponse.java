package com.synesis.mofl.lnm.payload;

import java.util.List;

import com.synesis.mofl.lnm.model.FormField;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is to get custom response of FormSetup model
 * 
 * @author Md Mushfiq Fuad
 * @since 05 Apr, 2022
 *
 */
@Getter
@Setter
public class FormSetupResponse {

    private Long id;
    private String baseType;
    private Object category;
    private Boolean hasFieldVerification;
    private Boolean hasLabTest;
    private Boolean hasApplicationPayment;
    private Boolean hasProcessPayment;
    private boolean isActive;
    private List<FormField> formField;
}
