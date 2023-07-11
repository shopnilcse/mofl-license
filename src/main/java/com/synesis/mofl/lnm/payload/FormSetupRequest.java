package com.synesis.mofl.lnm.payload;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * This payload class is for FormSetup Request
 * 
 * @author Md Mushfiq Fuad
 * @since 04 Apr, 2022
 */
@Getter
@Setter
public class FormSetupRequest {

    private String baseType;
    private Long categoryId;
    private List<FormFieldRequest> formFieldRequest;
}
