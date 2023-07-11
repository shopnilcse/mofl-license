package com.synesis.mofl.lnm.service.IService;

import com.synesis.mofl.lnm.model.LicenseVerification;
import com.synesis.mofl.lnm.payload.LicenseVerificationPayload;
import com.synesis.mofl.lnm.payload.LicenseVerificationResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 02 Feb, 2022
 * @version 1.1
 */
public interface ILicenseVerificationService {

    LicenseVerificationResponse getLicenseVerificationById(Long id) throws Exception;

    LicenseVerification getLicenseVerificationByappId(Long appId) throws Exception;

    LicenseVerification getLicenseVerificationByUserId(Long verifiedBy) throws Exception;

    LicenseVerification saveLicenseVerification(LicenseVerificationPayload licenseVerificationPayload) throws Exception;

    LicenseVerification updateLicenseVerification(LicenseVerificationPayload licenseVerificationPayload) throws Exception;
}
