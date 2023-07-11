package com.synesis.mofl.lnm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.helper.ModuleAdapter;
import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseVerification;
import com.synesis.mofl.lnm.payload.LicenseVerificationPayload;
import com.synesis.mofl.lnm.payload.LicenseVerificationResponse;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.repository.LicenseVerificationRepository;
import com.synesis.mofl.lnm.service.IService.ILicenseApplicationService;
import com.synesis.mofl.lnm.service.IService.ILicenseVerificationService;
/**
 * This service is to provide license verifications api
 *
 * @author Md. Emran Hossain
 * @since 02 Feb, 2022
 * @version 1.1
 */
@Service
public class LicenseVerificationService implements ILicenseVerificationService {

    @Value("${acl.url}")
    private String host;

    @Value("${endpoint.userdetails}")
    private String endpoint;
    
    @Autowired
    private LicenseVerificationRepository verificationRepository;

    @Autowired
    private LicenseApplicationRepository applicationRepository;
    
    @Autowired
    private ILicenseApplicationService iLicenseApplicationService;
    
    @Autowired
    private ModuleAdapter moduleAdapter;
    @Autowired 
    private HttpServletRequest request;

    /**
     * This api is for get License Verification Response by id
     *
     * @author Md. Emran Hossain
     * @author Md Mushfiq Fuad
     * @param  id - Long
     * @return LicenseVerificationResponse - Payload
     * @since  02 Feb, 2022
     */
    @Override
    public LicenseVerificationResponse getLicenseVerificationById(Long id)  throws Exception {
        LicenseVerification licenseVerification = verificationRepository.findById(id).orElseThrow();
        LicenseApplication hasApplication = applicationRepository.findById(licenseVerification.getLicenseApplication().getId()).orElseThrow();

        LicenseVerificationResponse licenseVerificationResponse = new LicenseVerificationResponse();
        licenseVerificationResponse.setId(licenseVerification.getId());
        licenseVerificationResponse.setVerificationType(licenseVerification.getVerificationType());
        licenseVerificationResponse.setReportAttachment(licenseVerification.getReportAttachment());
        licenseVerificationResponse.setDocumentAttachment(licenseVerification.getDocumentAttachment());
        licenseVerificationResponse.setLicenseApplicationResponse(iLicenseApplicationService.getLicenseApplicationById(hasApplication.getId()));
        licenseVerificationResponse.setRemarks(licenseVerification.getRemarks());
        
        Map<String, Object> userResponse = moduleAdapter.getData(request, host+ endpoint + licenseVerification.getCreatedBy());
        if(userResponse.isEmpty() || userResponse.get("email") == null || userResponse.get("email") == "") {
            // FIXME
            // Must be modify when ACL will ready
            licenseVerificationResponse.setVerifiedBy("Citizen User");
        } else {
            licenseVerificationResponse.setVerifiedBy(userResponse.get("fullName").toString());
        }
        return licenseVerificationResponse;
    }

    /**
     * This api is for get License Verification by application id
     *
     * @author Md. Emran Hossain
     * @param  appId - Long
     * @return LicenseVerification - Model
     * @since  02 Feb, 2022
     */
    @Override
    public LicenseVerification getLicenseVerificationByappId(Long appId)  throws Exception {
        LicenseApplication hasApplication = applicationRepository.findById(appId).orElseThrow();
        return verificationRepository.findByLicenseApplication(hasApplication);
    }

    /**
     * This api is for get License Verification by verified person id
     *
     * @author Md. Emran Hossain
     * @param  verifiedBy - Long
     * @return LicenseVerification - Model
     * @since  02 Feb, 2022
     */
    @Override
    public LicenseVerification getLicenseVerificationByUserId(Long verifiedBy) throws Exception {
        return verificationRepository.findByVerifiedBy(verifiedBy);
    }

    /**
     * This api is for Save License Verification
     *
     * @author Md. Emran Hossain
     * @param  requestData - Payload
     * @return LicenseVerification - Model
     * @since  02 Feb, 2022
     */
    @Transactional
    @Override
    public LicenseVerification saveLicenseVerification(LicenseVerificationPayload requestData) throws Exception {
        LicenseVerification verification = new LicenseVerification();
        LicenseApplication hasApplication = applicationRepository.findById(requestData.getLicenseApplicationId()).orElseThrow();

        verification.setVerificationType(requestData.getVerificationType());
        verification.setReportAttachment(requestData.getReportAttachment());
        verification.setDocumentAttachment(requestData.getDocumentAttachment());
        verification.setLicenseApplication(hasApplication);
        verification.setVerifiedBy(UserHelper.getUserId());
        verification.setRemarks(requestData.getRemarks());

        LicenseVerification responseData = verificationRepository.save(verification);
        return responseData;
    }

    /**
     * This api is for Update License Verification
     *
     * @author Md. Emran Hossain
     * @param  requestData - Payload
     * @return LicenseVerification - Model
     * @since  02 Feb, 2022
     */
    @Transactional
    @Override
    public LicenseVerification updateLicenseVerification(LicenseVerificationPayload requestData) throws Exception {

        LicenseVerification hasVerification = verificationRepository.findById(requestData.getId()).orElseThrow();

        if(requestData.getVerificationType() != null) {
            hasVerification.setVerificationType(requestData.getVerificationType());
        }
        if(requestData.getReportAttachment() != null) {
            hasVerification.setReportAttachment(requestData.getReportAttachment());
        }
        if(requestData.getDocumentAttachment() != null) {
            hasVerification.setDocumentAttachment(requestData.getDocumentAttachment());
        }
        hasVerification.setRemarks(requestData.getRemarks());

        LicenseVerification responseData = verificationRepository.save(hasVerification);
        return responseData;
    }
}
