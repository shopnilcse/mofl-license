package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.payload.FormFieldRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 02 Mar, 2022
 * @version 1.1
 */
public interface IFormFieldService {

    Page<FormField> getAllFormField(Pageable pageable) throws Exception;

    FormField getFormFieldById(Long id) throws Exception;

    FormSetupResponse getFormFieldByFormSetupId(Long formSetupId) throws Exception;

    FormField saveFormField(FormFieldRequest formFieldRequest) throws Exception;
    
    FormField updateFormField(FormFieldRequest formFieldRequest) throws Exception;

    void deleteFormField(Long id);

    void deleteFormFieldWithChecking(Long id, String baseType, Long categoryId) throws Exception;
    FormSetupResponse getFormFieldByTypeAndCategoryId(String type, Long categoryId) throws Exception;
}
