package com.synesis.mofl.lnm.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.model.LicenseVerification;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.model.NocVerification;
import com.synesis.mofl.lnm.model.Notice;
import com.synesis.mofl.lnm.payload.CertificatePayload;
import com.synesis.mofl.lnm.payload.LicenseApplicationPayload;
import com.synesis.mofl.lnm.payload.LicenseCategoryRequest;
import com.synesis.mofl.lnm.payload.LicenseNocAddressRequest;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingRequest;
import com.synesis.mofl.lnm.payload.LicenseVerificationPayload;
import com.synesis.mofl.lnm.payload.NocApplicationRequest;
import com.synesis.mofl.lnm.payload.NocCategoryRequest;
import com.synesis.mofl.lnm.payload.NocVerificationRequest;
import com.synesis.mofl.lnm.payload.NoticePayload;
/**
 * This class provide all mock data for perform test case
 * 
 * @author Md. Emran Hossain
 * @since 24 Feb, 2022
 * @version 1.1
 */
public class TestMockData {

    /**
     * Mock Data for depertment
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static Department getDepartment() {
        Department department = new Department();

        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        department.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        department.setCreatedBy(1L);
        department.setDescription("Something");
        department.setFullName("DR");
        department.setId(123L);
        department.setName("DLS");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        department.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        department.setUpdatedBy(1L);

        return department;
    }

    /**
     * Mock Data for form setup
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static FormSetup getFormSetup() {
        FormSetup formSetup = new FormSetup();

        formSetup.setBaseType("License");
        formSetup.setCategoryId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        formSetup.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        formSetup.setCreatedBy(1L);
        formSetup.setHasApplicationPayment(true);
        formSetup.setHasFieldVerification(true);
        formSetup.setHasLabTest(true);
        formSetup.setHasProcessPayment(true);
        formSetup.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        formSetup.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        formSetup.setUpdatedBy(1L);

        return formSetup;
    }

    /**
     * Mock Data for license category
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseCategory getLicenseCategory(Department department) {
        LicenseCategory licenseCategory = new LicenseCategory();

        licenseCategory.setCategoryName("MOFL");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseCategory.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        licenseCategory.setCreatedBy(1L);
        licenseCategory.setDepartmentId(1L);
        licenseCategory.setFormSetupId(1L);
        licenseCategory.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseCategory.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        licenseCategory.setUpdatedBy(1L);

        return licenseCategory;
    }

    /**
     * Mock Data for license application
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseApplication getLicenseApplication(LicenseCategory licenseCategory) {
        LicenseApplication licenseApplication = new LicenseApplication();

        licenseApplication.setApplicantId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseApplication.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        licenseApplication.setCreatedBy(1L);
        licenseApplication.setLicenseNocStatus(new LicenseNocStatus());
        licenseApplication.setId(123L);
        licenseApplication.setLicenseCategory(licenseCategory);
        licenseApplication.setNameOfNominee("Name Of Nominee");
        licenseApplication.setNationality("Nationality");
        licenseApplication.setNomineeNationality("Nominee Nationality");
        licenseApplication.setNomineePermanentAddress(getAddress());
        licenseApplication.setNomineeSignature("Nominee Signature");
        licenseApplication.setDynamicFields("Other Fields");
        licenseApplication.setPermanentAddress(getAddress());
        licenseApplication.setPhoneNo("4105551212");
        licenseApplication.setPresentAddress(getAddress());
        licenseApplication.setRelationWithNominee("Relation With Nominee");
        licenseApplication.setUid("1234");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseApplication.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        licenseApplication.setUpdatedBy(1L);

        return licenseApplication;
    }

    /**
     * Mock Data for license category payload
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseCategoryRequest getLicenseCategoryPayload() {
        LicenseCategoryRequest licenseCategoryRequest = new LicenseCategoryRequest();
        licenseCategoryRequest.setCategoryName("MOFL");
        licenseCategoryRequest.setDepartmentId(1L);
        licenseCategoryRequest.setFormSetupId(123L);
        licenseCategoryRequest.setId(123L);

        return licenseCategoryRequest;
    }

    /**
     * Mock Data for license application payload
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseApplicationPayload getLicenseApplicationPayload() {
        LicenseApplicationPayload licenseApplicationPayload = new LicenseApplicationPayload();

        licenseApplicationPayload.setApplicantId(123L);
        licenseApplicationPayload.setId(123L);
        licenseApplicationPayload.setLicenseCategoryId(123L);
        licenseApplicationPayload.setNameOfNominee("Name Of Nominee");
        licenseApplicationPayload.setNationality("Nationality");
        licenseApplicationPayload.setNomineeNationality("Nominee Nationality");
        licenseApplicationPayload.setNomineePermanentAddress(new LicenseNocAddressRequest());
        licenseApplicationPayload.setNomineeSignature("Nominee Signature");
        licenseApplicationPayload.setOtherFields("Other Fields");
        licenseApplicationPayload.setPermanentAddress(new LicenseNocAddressRequest());
        licenseApplicationPayload.setPhoneNo("4105551212");
        licenseApplicationPayload.setPresentAddress(new LicenseNocAddressRequest());
        licenseApplicationPayload.setRelationWithNominee("Relation With Nominee");
        licenseApplicationPayload.setUid("1234");

        return licenseApplicationPayload;
    }

    public static LicenseNocAddress getAddress() {
        LicenseNocAddress licenseNocAddress = new LicenseNocAddress();
        
        licenseNocAddress.setDivision(CommonMockData.getDivision());
        licenseNocAddress.setDistrict(CommonMockData.getDistrict(CommonMockData.getDivision()));
        licenseNocAddress.setUpazila(CommonMockData.getUpazila(CommonMockData.getDistrict(CommonMockData.getDivision())));
        licenseNocAddress.setAddressDetails("Address");

        return licenseNocAddress;
    }

    /**
     * Mock Data for certificate
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static Certificate getCertificate() {
        Certificate certificate = new Certificate();

        certificate.setApplicationId(123L);
        certificate.setCertificateName("Certificate Name");
        certificate.setCertificateType("Certificate Type");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        certificate.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        certificate.setCreatedBy(1L);
        certificate.setDescription("The characteristics of someone or something");
        certificate.setExpireDate(LocalDate.now());
        certificate.setId(123L);
        certificate.setIsActive(true);
        certificate.setIsArchived(true);
        certificate.setIssueDate(LocalDateTime.of(1, 1, 1, 1, 1));
        certificate.setIssuedBy(1L);
        certificate.setRejectionDate(LocalDate.now());
        certificate.setRemarks("Remarks");
        certificate.setRenewalDate(LocalDate.now());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        certificate.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        certificate.setUpdatedBy(1L);
        certificate.setUserId(123L);

        return certificate;
    }

    /**
     * Mock Data for certificate Payload
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static CertificatePayload getCertificatePayload() {
        CertificatePayload certificatePayload = new CertificatePayload();

        certificatePayload.setApplicationId(123L);
        certificatePayload.setCertificateName("Certificate Name");
        certificatePayload.setCertificateType("Certificate Type");
        certificatePayload.setDescription("The characteristics of someone or something");
        certificatePayload.setExpireDate(LocalDate.now());
        certificatePayload.setId(123L);
        certificatePayload.setIsActive(true);
        certificatePayload.setIsArchived(true);
        certificatePayload.setIssueDate(LocalDateTime.of(1, 1, 1, 1, 1));
        certificatePayload.setIssuedBy(1L);
        certificatePayload.setRejectionDate(LocalDate.now());
        certificatePayload.setRemarks("Remarks");
        certificatePayload.setRenewalDate(LocalDate.now());
        certificatePayload.setUserId(123L);

        return certificatePayload;
    }

    /**
     * Mock Data for License Noc Payment Setting
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseNocPaymentSetting getLicenseNocPaymentSetting() {
        LicenseNocPaymentSetting licenseNocPaymentSetting = new LicenseNocPaymentSetting();

        licenseNocPaymentSetting.setApplicationFee(10.0);
        licenseNocPaymentSetting.setBaseType("Base Type");
        licenseNocPaymentSetting.setCategoryId(123L);
        licenseNocPaymentSetting.setCertificateFee(10.0);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseNocPaymentSetting.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        licenseNocPaymentSetting.setCreatedBy(1L);
        licenseNocPaymentSetting.setHasApplicationFee(true);
        licenseNocPaymentSetting.setHasCertificateFee(true);
        licenseNocPaymentSetting.setHasLabtestFee(true);
        licenseNocPaymentSetting.setHasVerificationFee(true);
        licenseNocPaymentSetting.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseNocPaymentSetting.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        licenseNocPaymentSetting.setUpdatedBy(1L);
        licenseNocPaymentSetting.setVerificationFee(10.0);

        return licenseNocPaymentSetting;
    }

    /**
     * Mock Data for License Noc Payment Setting Request
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseNocPaymentSettingRequest getLicenseNocPaymentSettingRequest() {
        LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest = new LicenseNocPaymentSettingRequest();

        licenseNocPaymentSettingRequest.setApplicationFee(10.0);
        licenseNocPaymentSettingRequest.setBaseType("Base Type");
        licenseNocPaymentSettingRequest.setCategoryId(123L);
        licenseNocPaymentSettingRequest.setCertificateFee(10.0);
        licenseNocPaymentSettingRequest.setHasApplicationFee(true);
        licenseNocPaymentSettingRequest.setHasCertificateFee(true);
        licenseNocPaymentSettingRequest.setHasLabtestFee(true);
        licenseNocPaymentSettingRequest.setHasVerificationFee(true);
        licenseNocPaymentSettingRequest.setId(123L);
        licenseNocPaymentSettingRequest.setVerificationFee(10.0);

        return licenseNocPaymentSettingRequest;
    }

    /**
     * Mock Data for License Noc Payment Setting Request
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseVerification getLicenseVerification(LicenseApplication licenseApplication) {
        LicenseVerification licenseVerification = new LicenseVerification();

        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseVerification.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        licenseVerification.setCreatedBy(1L);
        licenseVerification.setDocumentAttachment("Document Attachment");
        licenseVerification.setId(123L);
        licenseVerification.setLicenseApplication(licenseApplication);
        licenseVerification.setReportAttachment("Report Attachment");
        licenseVerification.setRemarks("done");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        licenseVerification.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        licenseVerification.setUpdatedBy(1L);
        licenseVerification.setVerificationType("Verification Type");
        licenseVerification.setVerifiedBy(1L);

        return licenseVerification;
    }

    /**
     * Mock Data for License Verification Payload
     *
     * @author Md. Emran Hossain
     * @since 24 Jan, 2022
     */
    public static LicenseVerificationPayload getLicenseVerificationPayload() {
        LicenseVerificationPayload licenseVerificationPayload = new LicenseVerificationPayload();

        licenseVerificationPayload.setDocumentAttachment("Document Attachment");
        licenseVerificationPayload.setId(123L);
        licenseVerificationPayload.setLicenseApplicationId(123L);
        licenseVerificationPayload.setReportAttachment("Report Attachment");
        licenseVerificationPayload.setVerificationType("Verification Type");
        licenseVerificationPayload.setVerifiedBy(1L);
        licenseVerificationPayload.setRemarks("done");
        return licenseVerificationPayload;
    }

    /**
     * Mock Data for Noc category
     *
     * @author Md. Mushfiq Fuad
     * @since 25 Jan, 2022
     */
    public static NocCategory getNocCategory(Department department) {
        NocCategory nocCategory = new NocCategory();

        nocCategory.setCategoryName("MOFL");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocCategory.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        nocCategory.setCreatedBy(1L);
        nocCategory.setDepartmentId(1L);
        nocCategory.setFormSetupId(1L);
        nocCategory.setId(123L);
        nocCategory.setExpirationPeriod(13L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocCategory.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        nocCategory.setUpdatedBy(1L);

        return nocCategory;
    }

    /**
     * Mock Data for noc category payload
     *
     * @author Md. Mushfiq Fuad
     * @since 25 Jan, 2022
     */
    public static NocCategoryRequest getNocCategoryRequest() {
        NocCategoryRequest nocCategoryRequest = new NocCategoryRequest();

        nocCategoryRequest.setCategoryName("MOFL");
        nocCategoryRequest.setDepartmentId(123L);
        nocCategoryRequest.setFormSetupId(123L);
        nocCategoryRequest.setId(123L);

        return nocCategoryRequest;
    }

    /**
     * Mock NocApplication object
     */
    public static NocApplication getNocApplication(NocCategory nocCategory) {
        NocApplication nocApplication = new NocApplication();

        nocApplication.setApplicantId(123L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocApplication.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        nocApplication.setCreatedBy(1L);
        nocApplication.setId(123L);
        nocApplication.setNocCategory(nocCategory);
        nocApplication.setNameOfNominee("Name Of Nominee");
        nocApplication.setNationality("Nationality");
        nocApplication.setNomineeNationality("Nominee Nationality");
        nocApplication.setNomineePermanentAddress(getAddress());
        
        nocApplication.setNomineeSignature("Nominee Signature");
        nocApplication.setDynamicFields("Other Fields");
        nocApplication.setPermanentAddress(getAddress());
        nocApplication.setPhoneNo("4105551212");
        nocApplication.setPresentAddress(getAddress());
        nocApplication.setRelationWithNominee("Relation With Nominee");
        nocApplication.setUid("1234");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocApplication.setUpdatedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        nocApplication.setUpdatedBy(1L);

        return nocApplication;
    }

    /**
     * Mock NocVerification object
     */
    public static NocVerification getNocVerification(NocApplication nocApplication) {
        NocVerification nocVerification = new NocVerification();

        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocVerification.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        nocVerification.setCreatedBy(1L);
        nocVerification.setDocumentAttachment("Document Attachment");
        nocVerification.setId(123L);
        nocVerification.setNocApplication(nocApplication);
        nocVerification.setReportAttachment("Report Attachment");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        nocVerification.setUpdatedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        nocVerification.setUpdatedBy(1L);
        nocVerification.setLabTestReport("Lab Test");
        nocVerification.setVerificationType("Verification Type");
        nocVerification.setVerifiedBy(1L);

        return nocVerification;
    }

    /**
     * Mock NocVerification object
     */
    public static NocVerificationRequest getNocVerificationRequest() throws Exception {
        NocVerificationRequest nocVerificationRequest = new NocVerificationRequest();

        nocVerificationRequest.setDocumentAttachment("Document Attachment");
        nocVerificationRequest.setId(123L);
        nocVerificationRequest.setNocApplicationId(123L);
        nocVerificationRequest.setReportAttachment("Report Attachment");
        nocVerificationRequest.setLabTestReport("Lab Test");
        nocVerificationRequest.setVerificationType("Verification Type");
        nocVerificationRequest.setVerifiedBy(1L);

        return nocVerificationRequest;
    }

    public static Notice getNotice() {
        Notice notice = new Notice();

        notice.setAttachment("Attachment");
        notice.setBaseType("Base Type");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        notice.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        notice.setCreatedBy(1L);
        notice.setDepartmentId(123L);
        notice.setFromDate(LocalDate.ofEpochDay(1L));
        notice.setId(123L);
        notice.setIsActive(true);
        notice.setNoticeDetails("Notice Details");
        notice.setNoticeTitle("Dr");
        notice.setToDate(LocalDate.ofEpochDay(1L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        notice.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        notice.setUpdatedBy(1L);

        return notice;
    }

    public static NoticePayload getNoticePayload() {
        NoticePayload noticePayload = new NoticePayload();

        noticePayload.setBaseType("Base Type");
        noticePayload.setFromDate(LocalDate.ofEpochDay(1L));
        noticePayload.setId(123L);
        noticePayload.setAttachment("file path");

        noticePayload.setIsActive(true);
        noticePayload.setNoticeDetails("Notice Details");
        noticePayload.setNoticeTitle("Dr");
        noticePayload.setToDate(LocalDate.ofEpochDay(1L));

        return noticePayload;
    }
    
    /**
     * Mock NocApplicationRequest
     */
    public static NocApplicationRequest getNocApplicationRequest() {
        NocApplicationRequest nocApplicationRequest = new NocApplicationRequest();
        nocApplicationRequest.setApplicantId(123L);
        nocApplicationRequest.setId(123L);
        nocApplicationRequest.setNocCategoryId(123L);
        nocApplicationRequest.setNameOfNominee("Name Of Nominee");
        nocApplicationRequest.setNationality("Nationality");
        nocApplicationRequest.setNomineeNationality("Nominee Nationality");
        nocApplicationRequest.setNomineePermanentAddress(new LicenseNocAddressRequest());
        nocApplicationRequest.setNomineeSignature("Nominee Signature");
        nocApplicationRequest.setOtherFields("Other Fields");
        nocApplicationRequest.setPermanentAddress(new LicenseNocAddressRequest());
        nocApplicationRequest.setPhoneNo("4105551212");
        nocApplicationRequest.setPresentAddress(new LicenseNocAddressRequest());
        nocApplicationRequest.setRelationWithNominee("Relation With Nominee");
        nocApplicationRequest.setUid("1234");
        return nocApplicationRequest;
    }
}
