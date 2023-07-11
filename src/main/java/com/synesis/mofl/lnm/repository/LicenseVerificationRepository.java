package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseVerification;
/**
 * @author Md. Emran Hossain
 * @since 02 Feb, 2022
 * @version 1.1
 */
@Repository
public interface LicenseVerificationRepository extends JpaRepository<LicenseVerification, Long> {

    LicenseVerification findByLicenseApplication(LicenseApplication hasApplication);

    LicenseVerification findByVerifiedBy(Long verifiedBy);
}