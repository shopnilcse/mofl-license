package com.synesis.mofl.lnm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
/**
 * @author Md. Emran Hossain
 * @since 20 Jan, 2022
 * @version 1.1
 */
@Repository
public interface LicenseApplicationRepository extends JpaRepository<LicenseApplication, Long> {

    Page<LicenseApplication> findByApplicantId(Long applicantId, Pageable pageable);

    @Query("SELECT COUNT(la) FROM LicenseApplication la WHERE la.applicantId = :id")
    Long countLicenseApplicationByApplicantId(@Param("id") Long id);

    @Query("SELECT la FROM LicenseApplication la WHERE " +
            "(1 = :categoryTypeSearch OR la.licenseCategory.id = :categoryTypeId) " + 
            "AND (1 = :statusSearch OR la.licenseNocStatus.id = :applicationStatusId) " + 
            "AND (1 = :phoneNoSearch OR la.phoneNo = :phoneNo) " 
            + 
            "AND (1 = :dateSearch OR la.createdAt >= :fromDate AND la.createdAt <= :toDate)"
            )
    Page<LicenseApplication> getAllLicenseApplicationByCriteria(
            @Param("categoryTypeSearch") int categoryTypeSearch, @Param("categoryTypeId") Long categoryTypeId,
            @Param("statusSearch") int statusSearch, @Param("applicationStatusId") Long applicationStatusId,
            @Param("phoneNoSearch") int phoneNoSearch, @Param("phoneNo") String phoneNo,
            @Param("dateSearch") int dateSearch, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, 
            Pageable pageable);

    @Query("SELECT COUNT(la) FROM LicenseApplication la WHERE " +
            "(1 = :categoryTypeSearch OR la.licenseCategory.id = :categoryTypeId) " + 
            "AND (1 = :statusSearch OR la.licenseNocStatus.id = :applicationStatusId) " + 
            "AND (1 = :phoneNoSearch OR la.phoneNo = :phoneNo) " 
            + 
            "AND (1 = :dateSearch OR la.createdAt >= :fromDate AND la.createdAt <= :toDate)"
            )
    Long countApplicationByCriteria(
            @Param("categoryTypeSearch") int categoryTypeSearch, @Param("categoryTypeId") Long categoryTypeId,
            @Param("statusSearch") int statusSearch, @Param("applicationStatusId") Long applicationStatusId,
            @Param("phoneNoSearch") int phoneNoSearch, @Param("phoneNo") String phoneNo,
            @Param("dateSearch") int dateSearch, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    
    @Query("SELECT la FROM LicenseApplication la WHERE la.licenseCategory.departmentId = :departmentId")
    Page<LicenseApplication> getAllLicenseApplicationByDepartment(@Param("departmentId") Long departmentId, Pageable pageable);
    
    @Query("SELECT COUNT(la) FROM LicenseApplication la WHERE la.licenseCategory.departmentId = :departmentId")
    Long countApplicationByDepartment(@Param("departmentId") Long departmentId);

    List<LicenseApplication> findByLicenseCategoryId(Long categoryId);

//    Long countById(Long id);

    List<LicenseApplication> findByLicenseNocStatus(LicenseNocStatus hasLicenseNocStatus, Pageable pageable);

    Long countByLicenseNocStatus(LicenseNocStatus licenseNocStatus);
}
