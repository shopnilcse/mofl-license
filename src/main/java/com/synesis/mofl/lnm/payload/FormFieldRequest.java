package com.synesis.mofl.lnm.payload;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Emran Hossain
 * @since 21 Mar, 2022
 *
 */
@Getter
@Setter
public class FormFieldRequest {

    private Long id;
    private Long formSetupId;
    private String fieldTitle;
    private String fieldName;
    private String inputType;
    private Boolean isRequired;

    @Nullable
    private Integer position;

    @Nullable
    private String options;
}
