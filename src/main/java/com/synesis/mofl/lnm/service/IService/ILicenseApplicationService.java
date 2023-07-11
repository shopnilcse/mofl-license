package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.payload.LicenseApplicationPayload;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 20 Jan, 2022
 * @version 1.1
 */
public interface ILicenseApplicationService {
    Page<LicenseApplicationResponse> getAllLicenseApplication(Pageable pageable) throws Exception;
    Page<LicenseApplicationResponse> getLicenseApplicationByApplicantId(Long id, Pageable pageable) throws Exception;
    Page<LicenseApplicationResponse> getAllApprovedLicenseApplication(Pageable pageable) throws Exception;
    Page<LicenseApplicationResponse> getAllIssuedLicenseApplication(Pageable pageable) throws Exception;
    Page<LicenseApplicationResponse> getAllLicenseApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo, String fromDate, String toDate, Pageable pageable) throws Exception;
    Page<LicenseApplicationResponse> getAllLicenseApplicationDepartmentWise(Long departmentId, Pageable pageable) throws Exception;
    LicenseApplicationResponse getLicenseApplicationById(Long id) throws Exception;
    LicenseApplication saveLicenseApplication(LicenseApplicationPayload licenseApplicationPayload) throws Exception;

    LicenseApplication updateLicenseStatus(Long id, String status);
    Long countApplicationByType(Long id);
    Long countAll();
    Long countByLicenseApplicationByApplicantId(Long id);
    Long countApplicationByCriteria(Long categoryTypeId, Long applicationStatusId, String phoneNo, String fromDate, String toDate) throws Exception;
    Long countApplicationByDepartmentWise(Long departmentId) throws Exception;
}
