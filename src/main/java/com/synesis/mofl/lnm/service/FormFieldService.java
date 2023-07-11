package com.synesis.mofl.lnm.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesis.mofl.lnm.helper.FormFieldConverter;
import com.synesis.mofl.lnm.helper.FormSetupConverter;
import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.payload.FormFieldRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;
import com.synesis.mofl.lnm.repository.FormFieldRepository;
import com.synesis.mofl.lnm.repository.FormSetupRepository;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.service.IService.IFormFieldService;
/**
 * This service is to provide Form Fields api
 *
 * @author Md. Emran Hossain
 * @since 04 Apr, 2022
 * @version 1.1
 */
@Service
public class FormFieldService implements IFormFieldService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(FormFieldService.class);

    @Autowired
    private FormFieldRepository formFieldRepository;
    
    @Autowired
    private LicenseApplicationRepository licenseApplicationRepository;
    @Autowired
    private NocApplicationRepository nocApplicationRepository;

    @Autowired
    private FormSetupRepository formSetupRepository;

    @Autowired
    private FormFieldConverter formFieldConverter;

    @Autowired
    private FormSetupConverter formSetupConverter;

    /**
     * This api is for get all Form Fields
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since  04 Apr, 2022
     */
    @Override
    public Page<FormField> getAllFormField(Pageable pageable) throws Exception {
        return formFieldRepository.findAll(pageable);
    }

    /**
     * This api is for get Form Field by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return FormField - Model
     * @since  04 Apr, 2022
     */
    @Override
    public FormField getFormFieldById(Long id)  throws Exception {
        return formFieldRepository.findById(id).orElse(null);
    }

    /**
     * This api is for get Form Field by Form Setup id
     *
     * @author Md. Emran Hossain
     * @param  formSetupId - Long
     * @return FormField - Model
     * @since  04 Apr, 2022
     */
    @Override
    public FormSetupResponse getFormFieldByFormSetupId(Long formSetupId) throws Exception {
        FormSetup formSetup = formSetupRepository.findById(formSetupId).orElseThrow();
        FormSetupResponse formSetupResponse = formSetupConverter.convertFormSetupResponse(formSetup);

        formSetupResponse.setFormField(formFieldRepository.findByFormSetup(formSetup));
        return formSetupResponse;
    }

    /**
     * This method is for get Form Field by type and category id
     *
     * @author Md. Emran Hossain
     * @param  type - String
     * @param  categoryId - Long
     * @return ResponseEntity - ResponseEntity
     * @throws Exception - exception
     * @since 04 Apr, 2022
     */
    @Override
    public FormSetupResponse getFormFieldByTypeAndCategoryId(String type, Long categoryId) throws Exception {

        FormSetup formSetup = formSetupRepository.findByBaseTypeIgnoreCaseAndCategoryIdAndIsActive(type, categoryId, true);
        FormSetupResponse formSetupResponse = new FormSetupResponse();
        if(!ObjectUtils.isEmpty(formSetup)) {
            formSetupResponse = formSetupConverter.convertFormSetupResponse(formSetup);

            formSetupResponse.setFormField(formFieldRepository.findByFormSetup(formSetup));
        }

        return formSetupResponse;
    }

    /**
     * This method save Form Field
     *
     * @author Md. Emran Hossain
     * @param  formFieldRequest - FormFieldRequest
     * @return formField - FormField
     * @since 06 Apr, 2022
     */
    @Override
    public FormField saveFormField(FormFieldRequest formFieldRequest) throws Exception {
        FormSetup formSetup = formSetupRepository.findById(formFieldRequest.getFormSetupId()).orElseThrow();

        FormField responseData =  formFieldConverter.convertRequestToEntity(formFieldRequest);
        responseData.setFormSetup(formSetup);

        return formFieldRepository.save(responseData);
    }
    
    @Override
    public FormField updateFormField(FormFieldRequest formFieldRequest) throws Exception {
        FormField existedFormField = formFieldRepository.findById(formFieldRequest.getId()).orElseThrow(() -> new EntityNotFoundException());
        if(ObjectUtils.isEmpty(existedFormField)) {
            return null;
        }
        FormSetup formSetup = formSetupRepository.findById(formFieldRequest.getFormSetupId()).orElseThrow();

        existedFormField =  formFieldConverter.convertRequestToEntity(formFieldRequest);
        existedFormField.setFormSetup(formSetup);

        return formFieldRepository.save(existedFormField);
    }

    /**
     * This api is for Delete Form Field
     *
     * @author Md. Emran Hossain
     * @param id - Long
     * @since 06 Apr, 2022
     */
    @Override
    public void deleteFormField(Long id) {
        formFieldRepository.deleteById(id);
    }

    /**
     * This method is for deleting existing form field, if there is no dependency
     * 
     * @author Md Mushfiq Fuad
     * @since 10 Apr, 2022
     * @param id - Long id
     * @param baseType - String license/noc
     * @param categoryId - Long categoryId
     */
    @Override
    public void deleteFormFieldWithChecking(Long id, String baseType, Long categoryId) throws Exception {
        FormField existedFormField = formFieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        if(!ObjectUtils.isEmpty(existedFormField)) {
            Object application = new Object();
            if(baseType.equalsIgnoreCase("license")) {
                application = licenseApplicationRepository.findByLicenseCategoryId(categoryId);
            }
            else if(baseType.equalsIgnoreCase("noc")) {
                application = nocApplicationRepository.findByNocCategoryId(categoryId);
            }
            if(ObjectUtils.isEmpty(application)) {
                formFieldRepository.deleteById(id);
            }
            else throw new EntityExistsException();
        }
        
    }
}
