package com.synesis.mofl.lnm.repository;

import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.payload.ApplicationFeeForLicenseNocDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Md. Kamruzzaman
 * @since 23 March, 2022
 * @version 1.1
 */
@Repository
public interface ApplicationFeeForLicenseNocRepository  extends JpaRepository<LicenseNocPaymentSetting, Long> {

     @Query(value="select license_categories.category_name as categoryName ,\n" +
             "license_noc_statuses.name as applicationStatus,\n" +
             "license_applications.uid as uid,\n" +
             "license_applications.applicant_id as applicantId,\n" +
             "license_noc_payment_settings.application_fee as applicationFee,\n" +
             "license_noc_payment_settings.verification_fee as verificationFee ,\n" +
             "license_noc_payment_settings.certificate_fee as certificateFee , \n" +
             "license_noc_payment_settings.has_application_fee as hasapplicationFee,\n" +
             "license_noc_payment_settings.has_verification_fee as hasverificationFee,\n" +
             "license_noc_payment_settings.has_certificate_fee as hasCertificateFee\n" +
             "from license_applications " +
             "join license_noc_payment_settings on license_noc_payment_settings.base_type='license' and license_noc_payment_settings.category_id = license_applications.license_category_id " +
             "join license_categories on license_categories.id=license_noc_payment_settings.category_id " + 
             "join license_noc_statuses on license_applications.license_noc_status_id=license_noc_statuses.id " +
             "where license_applications.id=:applicationId",nativeQuery = true)
     List<ApplicationFeeForLicenseNocDTO> getApplicationPaymentInfoForLincense(@Param("applicationId") Long applicationId );

     @Query(value="select license_categories.category_name as categoryName ,\n" +
             "license_noc_statuses.name as applicationStatus,\n" +
             "license_applications.uid as uid,\n" +
             "license_applications.applicant_id as applicantId,\n" +
             "license_noc_payment_settings.application_fee as applicationFee,\n" +
             "license_noc_payment_settings.verification_fee as verificationFee ,\n" +
             "license_noc_payment_settings.certificate_fee as certificateFee , \n" +
             "license_noc_payment_settings.has_application_fee as hasapplicationFee,\n" +
             "license_noc_payment_settings.has_verification_fee as hasverificationFee,\n" +
             "license_noc_payment_settings.has_certificate_fee as hasCertificateFee\n" +
             "from license_applications " +
             "join license_noc_payment_settings on license_noc_payment_settings.base_type='noc' and license_noc_payment_settings.category_id = license_applications.license_category_id " +
             "join license_categories on license_categories.id=license_noc_payment_settings.category_id " +
             "join license_noc_statuses on license_applications.license_noc_status_id=license_noc_statuses.id " +
             "where license_applications.id=:applicationId",nativeQuery = true)
     List<ApplicationFeeForLicenseNocDTO> getApplicationPaymentInfoForNoc(@Param("applicationId") Long applicationId );
}
