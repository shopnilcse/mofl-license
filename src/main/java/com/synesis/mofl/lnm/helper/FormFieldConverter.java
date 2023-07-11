package com.synesis.mofl.lnm.helper;

import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.payload.FormFieldRequest;
/**
 * @author Md. Emran Hossain
 * @since 04 Apr, 2022
 * @version 1.1
 */
@Component
public class FormFieldConverter {

    /**
     * This method convert request to entity
     * 
     * @author Md. Emran Hossain
     * @param formFieldRequest - request
     * @return FormField - entity
     * @since 04 Apr, 2022
     */
    public FormField convertRequestToEntity(FormFieldRequest formFieldRequest){
        FormField responseData = new FormField();

        responseData.setFieldTitle(formFieldRequest.getFieldTitle());
        responseData.setFieldName(formFieldRequest.getFieldName());
        responseData.setInputType(formFieldRequest.getInputType());
        responseData.setIsRequired(formFieldRequest.getIsRequired());
        responseData.setPosition(formFieldRequest.getPosition());
        responseData.setOptions(formFieldRequest.getOptions());

        return responseData;
    }
}
