package com.synesis.mofl.lnm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.helper.FormFieldConverter;
import com.synesis.mofl.lnm.helper.FormSetupConverter;
import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.payload.FormFieldRequest;
import com.synesis.mofl.lnm.payload.FormSetupRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;
import com.synesis.mofl.lnm.repository.FormFieldRepository;
import com.synesis.mofl.lnm.repository.FormSetupRepository;
import com.synesis.mofl.lnm.service.IService.IFormSetupService;
import com.synesis.mofl.lnm.service.IService.ILicenseNocPaymentSettingService;

/**
 * @author Md Mushfiq Fuad
 * @since 04 Apr, 2022
 */
@Service
public class FormSetupService implements IFormSetupService{

    @Autowired
    private FormSetupConverter formSetupConverter;
    @Autowired
    private FormSetupRepository formSetupRepository;
    @Autowired
    private ILicenseNocPaymentSettingService iLicenseNocPaymentSettingService;

    @Autowired
    private FormFieldConverter formFieldConverter;

    @Autowired
    private FormFieldRepository formFieldRepository;
    
    /**
     * @param pageable - Pageable
     * @exception Exception - exception
     */
    @Override
    public Page<FormSetupResponse> getAllFormSetup(Pageable pageable) throws Exception{
        List<FormSetupResponse> responseData = new ArrayList<FormSetupResponse>();
        Page<FormSetup> formSetups = formSetupRepository.findAll(pageable);

        for(FormSetup formSetup : formSetups.getContent()) {
            FormSetupResponse formSetupResponse = formSetupConverter.convertFormSetupResponse(formSetup);
            responseData.add(formSetupResponse);
        }

        Page<FormSetupResponse> page = new PageImpl<>(responseData);
        return page;
    }
    
    /**
     * This method is to get FormSetupResponse by Form Setup Id
     * 
     * @author Md Mushfiq Fuad
     * @since 05 Apr, 2022
     * @param id - Long id
     * @exception Exception - Exception
     * @return FormSetupResponse - payload
     */
    @Override
    public FormSetupResponse getFormSetupResponseById(Long id) throws Exception {
        FormSetup existedFormSetup = formSetupRepository.findById(id).orElseThrow();
        FormSetupResponse formSetupResponse = formSetupConverter.convertFormSetupResponse(existedFormSetup);
        return formSetupResponse;
    }
    /**
     * This method is to save FormSetup in existing database
     * 
     * @author Md Mushfiq Fuad
     * @since 05 Apr, 2022
     * @param formSetupRequest - payload
     * @throws Exception - Exception
     * @return FormField - List
     */
    @Transactional
    @Override
    public List<FormField> saveFormSetup(FormSetupRequest formSetupRequest) throws Exception {
        FormSetup existedFormSetup = formSetupRepository.findByBaseTypeIgnoreCaseAndCategoryId(formSetupRequest.getBaseType(), formSetupRequest.getCategoryId());
        if(!ObjectUtils.isEmpty(existedFormSetup)) {
            throw new EntityExistsException();
        }
        LicenseNocPaymentSetting existedLicenseNocPaymentSetting = iLicenseNocPaymentSettingService.hasPaymentSetting(formSetupRequest.getBaseType(), formSetupRequest.getCategoryId());
        
        if(ObjectUtils.isEmpty(existedLicenseNocPaymentSetting)) {
            throw new NoSuchElementException();
        }

        FormSetup savedFormSetup = new FormSetup();
        savedFormSetup.setBaseType(existedLicenseNocPaymentSetting.getBaseType().toLowerCase());
        savedFormSetup.setCategoryId(existedLicenseNocPaymentSetting.getCategoryId());
        savedFormSetup.setHasApplicationPayment(existedLicenseNocPaymentSetting.getHasApplicationFee());
        savedFormSetup.setHasFieldVerification(existedLicenseNocPaymentSetting.getHasVerificationFee());
        savedFormSetup.setHasLabTest(existedLicenseNocPaymentSetting.getHasLabtestFee());
        savedFormSetup.setHasProcessPayment(existedLicenseNocPaymentSetting.getHasCertificateFee());
        savedFormSetup.setActive(true);
        FormSetup result = formSetupRepository.save(savedFormSetup);

        if(ObjectUtils.isEmpty(result)) {
            throw new Exception();
        }

        List<FormField> formFieldRequestList = new ArrayList<FormField>();
        for(FormFieldRequest formFieldRequest: formSetupRequest.getFormFieldRequest()) {
            formFieldRequest.setFormSetupId(result.getId());

            FormField responseData =  formFieldConverter.convertRequestToEntity(formFieldRequest);
            responseData.setFormSetup(result);

            formFieldRequestList.add(responseData);
        }

        return formFieldRepository.saveAll(formFieldRequestList);
    }
    
    @Override
    public Long countAll() {
        Long element = formSetupRepository.count();
        return element;
    }

    @Override
    public Page<FormSetupResponse> getAllFormSetupByType(String type, Pageable pageable) throws Exception {
        List<FormSetupResponse> responseData = new ArrayList<FormSetupResponse>();
        Page<FormSetup> formSetups = formSetupRepository.findByBaseTypeIgnoreCase(type, pageable);

        for(FormSetup formSetup : formSetups.getContent()) {
            FormSetupResponse formSetupResponse = formSetupConverter.convertFormSetupResponse(formSetup);
            responseData.add(formSetupResponse);
        }

        Page<FormSetupResponse> page = new PageImpl<>(responseData);
        return page;
    }


    @Override
    public Long countByBaseType(String type) {
        Long element = formSetupRepository.countByBaseTypeIgnoreCase(type);
        return element;
    }

    @Override
    public Boolean hasSetupExist(String type, Long categoryId) {
        Boolean hasExist = false;
        FormSetup existed = formSetupRepository.findByBaseTypeIgnoreCaseAndCategoryIdAndIsActive(type, categoryId, true);
        if(ObjectUtils.isEmpty(existed)) {
            return hasExist;
        }
        hasExist = true;
        return hasExist;
    }

    /**
     * This method is to update isActive status
     * 
     * @author Md Mushfiq Fuad
     * @since 09 Apr, 2022
     * @param id - Long formSetupId
     * @param status - boolean status
     * @throws Exception - Exception
     * @return FormSetup - model
     */
    @Transactional
    @Override
    public FormSetup updateIsActivate(Long id, boolean status) throws Exception{
        FormSetup existedFormSetup = formSetupRepository.findById(id).orElseThrow(() ->new EntityNotFoundException());
        existedFormSetup.setActive(status);
        FormSetup updatedFormSetup = formSetupRepository.save(existedFormSetup);
        return updatedFormSetup;
    }}
