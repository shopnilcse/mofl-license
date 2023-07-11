package com.synesis.mofl.lnm.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.model.NocVerification;
import com.synesis.mofl.lnm.payload.NocVerificationRequest;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.repository.NocVerificationRepository;
import com.synesis.mofl.lnm.service.IService.INocVerificationService;

/**
 * This service is to provide all implementation of Noc Verification related api's
 * @author Md Mushfiq Fuad
 * @since 01 Feb, 2022
 * @version 1.1
 * 
 */
@Service
public class NocVerificationService implements INocVerificationService {

    @Autowired
    private NocVerificationRepository nocVerificationRepository;
    @Autowired
    private NocApplicationRepository nocApplicationRepository;
    
    /**
     * This method is to get Noc Verification by Id
     * @param id - Long id
     * @return NocVerification - object
     */
    @Override
    public NocVerification getNocVerificationById(Long id) {
        return nocVerificationRepository.findById(id).orElse(null);
    }

    /**
     * This method is to update NocVerification in database
     * @since 01 Feb, 2022
     * @param nocVerificationRequest - payload
     * @return NocVerification - response object 
     */
    @Transactional
    @Override
    public NocVerification saveNocVerification(NocVerificationRequest nocVerificationRequest) throws Exception{
        NocVerification nocVerification = new NocVerification();
        NocApplication nocApplication = nocApplicationRepository.findById(nocVerificationRequest.getNocApplicationId()).orElseThrow();
        
        nocVerification.setId(nocVerificationRequest.getId());
        nocVerification.setVerificationType(nocVerificationRequest.getVerificationType());
        nocVerification.setReportAttachment(nocVerificationRequest.getReportAttachment());
        nocVerification.setDocumentAttachment(nocVerificationRequest.getDocumentAttachment());
        nocVerification.setLabTestReport(nocVerificationRequest.getLabTestReport());
        nocVerification.setNocApplication(nocApplication);
        nocVerification.setVerifiedBy(UserHelper.getUserId());
        NocVerification savedNocVerification = nocVerificationRepository.save(nocVerification);
        return savedNocVerification;
    }

    /**
     * This method is to update NocVerification in database
     * @since 01 Feb, 2022
     * @param nocVerificationRequest - payload
     * @return NocVerification - response object
     */
    @Transactional
    @Override
    public NocVerification updateNocVerification(NocVerificationRequest nocVerificationRequest) throws Exception {
        NocVerification existedNocVerification = nocVerificationRepository.findById(nocVerificationRequest.getId()).orElse(null);
        if(ObjectUtils.isEmpty(existedNocVerification)) {
            return null;
        }
        if(nocVerificationRequest.getVerificationType() != null) {
            existedNocVerification.setVerificationType(nocVerificationRequest.getVerificationType());
        }
        if(nocVerificationRequest.getReportAttachment() != null) {
            existedNocVerification.setReportAttachment(nocVerificationRequest.getReportAttachment());
        }
        if(nocVerificationRequest.getDocumentAttachment() != null) {
            existedNocVerification.setDocumentAttachment(nocVerificationRequest.getDocumentAttachment());
        }
        if(nocVerificationRequest.getLabTestReport() != null) {
            existedNocVerification.setLabTestReport(nocVerificationRequest.getLabTestReport());
        }
        NocVerification updatedNocVerification = nocVerificationRepository.save(existedNocVerification);
        return updatedNocVerification;
    }

    /**
     * This method is to get Noc Verification by Application Id
     * @param id - Long applicationId
     * @return NocVerification - object
     */
    @Override
    public NocVerification getNocVerificationByAppId(Long id) {
        NocApplication hasApplication = nocApplicationRepository.findById(id).orElse(null);
        if(ObjectUtils.isEmpty(hasApplication)) {
            return null;
        }
        return nocVerificationRepository.findByNocApplication(hasApplication);
    }

    /**
     * This method is to get Noc Verification by User Id
     * @param id - Long verified user id
     * @return NocVerification - object
     */
    @Override
    public NocVerification getNocVerificationByVerifiedBy(Long id) {
        return nocVerificationRepository.findByVerifiedBy(id).orElse(null);
    }

}
