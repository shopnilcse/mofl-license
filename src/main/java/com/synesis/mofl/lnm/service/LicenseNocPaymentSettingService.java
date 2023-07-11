package com.synesis.mofl.lnm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingRequest;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingResponse;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.repository.LicenseNocPaymentSettingRepository;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;
import com.synesis.mofl.lnm.service.IService.ILicenseNocPaymentSettingService;

/**
 * This service is to provide all implementation of License Noc Payment Settings related api's
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Service
public class LicenseNocPaymentSettingService implements ILicenseNocPaymentSettingService{

    @Autowired
    private LicenseNocPaymentSettingRepository licenseNocPaymentSettingRepository;
    
    @Autowired
    private LicenseCategoryRepository licenseCategoryRepository;
    @Autowired
    private NocCategoryRepository nocCategoryRepository;
    /**
     * This method is to retreive all License Noc Payment Settings 
     * @param pageable - Pageable
     * @return Page - Page object
     */
    @Override
    public Page<LicenseNocPaymentSetting> getAllLicenseNocPaymentSettings(Pageable pageable) {
         return licenseNocPaymentSettingRepository.findAll(pageable);
    }

    /**
     * This method is to retreive all License Payment Settings 
     * 
     * @author Md Mushfiq Fuad
     * @since 20 Mar, 2022
     * @param pageable - Pageable
     * @return Page - Page object
     */
    @Override
    public Page<LicenseNocPaymentSettingResponse> getAllLicensePaymentSettings(Pageable pageable) {
        String baseType = "license";
        List<LicenseNocPaymentSettingResponse> licenseNocPaymentSettingResponses = new ArrayList<LicenseNocPaymentSettingResponse>();
        
        Page<LicenseNocPaymentSetting> licensePaymentSettings = licenseNocPaymentSettingRepository.findAllByBaseType(baseType, pageable);
        for(LicenseNocPaymentSetting licensePS: licensePaymentSettings) {
            LicenseNocPaymentSettingResponse licenseNocPaymentSettingResponse = new LicenseNocPaymentSettingResponse();
            licenseNocPaymentSettingResponse.setId(licensePS.getId());
            licenseNocPaymentSettingResponse.setBaseType(licensePS.getBaseType());
            licenseNocPaymentSettingResponse.setCategory(licenseCategoryRepository.getById(licensePS.getCategoryId()));
            licenseNocPaymentSettingResponse.setApplicationFee(licensePS.getApplicationFee());
            licenseNocPaymentSettingResponse.setCertificateFee(licensePS.getCertificateFee());
            licenseNocPaymentSettingResponse.setVerificationFee(licensePS.getVerificationFee());
            licenseNocPaymentSettingResponse.setHasApplicationFee(licensePS.getHasApplicationFee());
            licenseNocPaymentSettingResponse.setHasCertificateFee(licensePS.getHasCertificateFee());
            licenseNocPaymentSettingResponse.setHasVerificationFee(licensePS.getHasVerificationFee());
            licenseNocPaymentSettingResponse.setHasLabtestFee(licensePS.getHasLabtestFee());
            licenseNocPaymentSettingResponses.add(licenseNocPaymentSettingResponse);
        }
        Page<LicenseNocPaymentSettingResponse> page = new PageImpl<>(licenseNocPaymentSettingResponses);
        return page;
    }
    
    /**
     * This method returns total count of payments
     * 
     * @author Md Mushfiq Fuad
     * @since 20 Mar, 2022
     */
    public Long countByBaseTypeIgnoreCase(String type) {
        return licenseNocPaymentSettingRepository.countByBaseTypeIgnoreCase(type);
    }

    /**
     * This method is to retreive all Noc Payment Settings 
     * 
     * @author Md Mushfiq Fuad
     * @since 20 Mar, 2022
     * @param pageable - Pageable
     * @return Page - Page object
     */
    @Override
    public Page<LicenseNocPaymentSettingResponse> getAllNocPaymentSettings(Pageable pageable) {
        String baseType = "noc";
        List<LicenseNocPaymentSettingResponse> licenseNocPaymentSettingResponses = new ArrayList<LicenseNocPaymentSettingResponse>();
        Page<LicenseNocPaymentSetting> nocPaymentSettings = licenseNocPaymentSettingRepository.findAllByBaseType(baseType, pageable);
        for(LicenseNocPaymentSetting nocPS: nocPaymentSettings) {
            LicenseNocPaymentSettingResponse licenseNocPaymentSettingResponse = new LicenseNocPaymentSettingResponse();
            licenseNocPaymentSettingResponse.setId(nocPS.getId());
            licenseNocPaymentSettingResponse.setBaseType(nocPS.getBaseType());
            licenseNocPaymentSettingResponse.setCategory(nocCategoryRepository.getById(nocPS.getCategoryId()));
            licenseNocPaymentSettingResponse.setApplicationFee(nocPS.getApplicationFee());
            licenseNocPaymentSettingResponse.setCertificateFee(nocPS.getCertificateFee());
            licenseNocPaymentSettingResponse.setVerificationFee(nocPS.getVerificationFee());
            licenseNocPaymentSettingResponse.setHasApplicationFee(nocPS.getHasApplicationFee());
            licenseNocPaymentSettingResponse.setHasCertificateFee(nocPS.getHasCertificateFee());
            licenseNocPaymentSettingResponse.setHasVerificationFee(nocPS.getHasVerificationFee());
            licenseNocPaymentSettingResponse.setHasLabtestFee(nocPS.getHasLabtestFee());
            licenseNocPaymentSettingResponses.add(licenseNocPaymentSettingResponse);
        }
        Page<LicenseNocPaymentSettingResponse> page = new PageImpl<>(licenseNocPaymentSettingResponses);
        return page;
    }
    
    /**
     * This method is to get LicenseNocPaymentSetting by id
     * @param id - Long LicenseNocPaymentSetting id
     * @return LicenseNocPaymentSetting - LicenseNocPaymentSetting object
     */
    @Override
    public LicenseNocPaymentSetting getLicenseNocPaymentSettingById(Long id) {
        return licenseNocPaymentSettingRepository.findById(id).orElse(null);
    }
    
    /**
     * This method is to get LicenseNocPaymentSettingDetails by id
     * @param id - Long LicenseNocPaymentSetting id
     * @return LicenseNocPaymentSettingResponse - payload
     */
    @Override
    public LicenseNocPaymentSettingResponse getLicenseNocPaymentSettingDetail(Long id) {
        LicenseNocPaymentSettingResponse licenseNocPaymentSettingResponse = new LicenseNocPaymentSettingResponse();

        LicenseNocPaymentSetting licenseNocPaymentSetting = licenseNocPaymentSettingRepository.findById(id).orElseThrow();
        licenseNocPaymentSettingResponse.setId(id);
        licenseNocPaymentSettingResponse.setBaseType(licenseNocPaymentSetting.getBaseType());
        
        if(licenseNocPaymentSetting.getBaseType().equalsIgnoreCase("License")) {
            Optional<LicenseCategory> tempLicenseCategory = licenseCategoryRepository.findById(licenseNocPaymentSetting.getCategoryId());
            licenseNocPaymentSettingResponse.setCategory(tempLicenseCategory);
        }
        else if(licenseNocPaymentSetting.getBaseType().equalsIgnoreCase("Noc")) {
            Optional<NocCategory> tempNocCategory = nocCategoryRepository.findById(licenseNocPaymentSetting.getCategoryId());
            licenseNocPaymentSettingResponse.setCategory(tempNocCategory);
        }
        licenseNocPaymentSettingResponse.setApplicationFee(licenseNocPaymentSetting.getApplicationFee());
        licenseNocPaymentSettingResponse.setCertificateFee(licenseNocPaymentSetting.getCertificateFee());
        licenseNocPaymentSettingResponse.setVerificationFee(licenseNocPaymentSetting.getVerificationFee());
        licenseNocPaymentSettingResponse.setHasApplicationFee(licenseNocPaymentSetting.getHasApplicationFee());
        licenseNocPaymentSettingResponse.setHasCertificateFee(licenseNocPaymentSetting.getHasCertificateFee());
        licenseNocPaymentSettingResponse.setHasVerificationFee(licenseNocPaymentSetting.getHasVerificationFee());
        licenseNocPaymentSettingResponse.setHasLabtestFee(licenseNocPaymentSetting.getHasLabtestFee());
        
        return licenseNocPaymentSettingResponse;
    }

    /**
     * This method is to save LicenseNocPaymentSetting
     * @param licenseNocPaymentSettingRequest - LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest
     * @return LicenseNocPaymentSetting - LicenseNocPaymentSetting object
     */
    @Transactional
    @Override
    public LicenseNocPaymentSetting saveLicenseNocPaymentSetting(LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) throws Exception {

        LicenseNocPaymentSetting existedlicenseNocPaymentSetting = licenseNocPaymentSettingRepository.findByBaseTypeIgnoreCaseAndCategoryId(licenseNocPaymentSettingRequest.getBaseType(), licenseNocPaymentSettingRequest.getCategoryId());
        if(!ObjectUtils.isEmpty(existedlicenseNocPaymentSetting)) {
            throw new EntityExistsException();
        }
        LicenseNocPaymentSetting licenseNocPaymentSetting = new LicenseNocPaymentSetting();
        licenseNocPaymentSetting.setBaseType(licenseNocPaymentSettingRequest.getBaseType().toLowerCase());
        licenseNocPaymentSetting.setCategoryId(licenseNocPaymentSettingRequest.getCategoryId());
        licenseNocPaymentSetting.setApplicationFee(licenseNocPaymentSettingRequest.getApplicationFee());
        licenseNocPaymentSetting.setCertificateFee(licenseNocPaymentSettingRequest.getCertificateFee());
        licenseNocPaymentSetting.setVerificationFee(licenseNocPaymentSettingRequest.getVerificationFee());
        licenseNocPaymentSetting.setHasApplicationFee(licenseNocPaymentSettingRequest.getHasApplicationFee());
        licenseNocPaymentSetting.setHasCertificateFee(licenseNocPaymentSettingRequest.getHasCertificateFee());
        licenseNocPaymentSetting.setHasLabtestFee(licenseNocPaymentSettingRequest.getHasLabtestFee());
        licenseNocPaymentSetting.setHasVerificationFee(licenseNocPaymentSettingRequest.getHasVerificationFee());
        
        
        LicenseNocPaymentSetting savedLicenseNocPaymentSetting = licenseNocPaymentSettingRepository.save(licenseNocPaymentSetting);
        return savedLicenseNocPaymentSetting;
    }

    /**
     * This method is to update LicenseNocPaymentSetting
     * @param licenseNocPaymentSettingRequest - LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest
     * @return LicenseNocPaymentSetting - LicenseNocPaymentSetting object
     */
    @Transactional
    @Override
    public LicenseNocPaymentSetting updateLicenseNocPaymentSetting(LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) throws Exception {
        LicenseNocPaymentSetting existedLicenseNocPaymentSetting = new LicenseNocPaymentSetting();
        existedLicenseNocPaymentSetting = licenseNocPaymentSettingRepository.findById(licenseNocPaymentSettingRequest.getId()).orElse(null);
        if(ObjectUtils.isEmpty(existedLicenseNocPaymentSetting)) {
            return null;
        }
        
        if(!ObjectUtils.isEmpty(licenseNocPaymentSettingRequest.getApplicationFee())) {
            existedLicenseNocPaymentSetting.setApplicationFee(licenseNocPaymentSettingRequest.getApplicationFee());
        }
        
        if(!ObjectUtils.isEmpty(licenseNocPaymentSettingRequest.getCertificateFee())) {
            existedLicenseNocPaymentSetting.setCertificateFee(licenseNocPaymentSettingRequest.getCertificateFee());
        }
        if(!ObjectUtils.isEmpty(licenseNocPaymentSettingRequest.getVerificationFee())) {
            existedLicenseNocPaymentSetting.setVerificationFee(licenseNocPaymentSettingRequest.getVerificationFee());
        }
        
        
        existedLicenseNocPaymentSetting.setHasApplicationFee(licenseNocPaymentSettingRequest.getHasApplicationFee());
        existedLicenseNocPaymentSetting.setHasCertificateFee(licenseNocPaymentSettingRequest.getHasCertificateFee());
        existedLicenseNocPaymentSetting.setHasLabtestFee(licenseNocPaymentSettingRequest.getHasLabtestFee());
        existedLicenseNocPaymentSetting.setHasVerificationFee(licenseNocPaymentSettingRequest.getHasVerificationFee());
        
        
        
        LicenseNocPaymentSetting updatedLicenseNocPaymentSetting = licenseNocPaymentSettingRepository.save(existedLicenseNocPaymentSetting);
        return updatedLicenseNocPaymentSetting;
    }

    @Override
    public LicenseNocPaymentSetting hasPaymentSetting(String type, Long categoryId) {

        return licenseNocPaymentSettingRepository.findByBaseTypeIgnoreCaseAndCategoryId(type, categoryId);
    }
}
