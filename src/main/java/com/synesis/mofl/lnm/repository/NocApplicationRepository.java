package com.synesis.mofl.lnm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.payload.NocApplicationLabTestDTO;

/**
 * This repository for Noc Application related queries
 * 
 * @author Md Mushfiq Fuad
 * @since 01 Feb, 2022
 *
 */
@Repository
public interface NocApplicationRepository extends JpaRepository<NocApplication, Long> {

    Page<NocApplication> findByApplicantId(Long id, Pageable pageable);

    @Query(value="Select noc_applications.uid as uid, noc_applications.id as nocApplicationId, " +
            "noc_applications.applicant_id as applicantId, noc_applications.noc_category_id as categoryId, " +
            "license_noc_statuses.name as applicationStatus, noc_categories.category_name as categoryName," +
            "array_to_string(array_agg(noc_lab_tests.lab_test_id), ',') as labTestId " +
            "From noc_applications " +
            "JOIN noc_categories ON noc_applications.noc_category_id=noc_categories.id " +
            "JOIN noc_lab_tests ON noc_applications.noc_category_id=noc_lab_tests.noc_category_id " +
            "join license_noc_statuses on noc_applications.license_noc_status_id=license_noc_statuses.id " +
            "WHERE noc_applications.application_status = ?1 " +
            "GROUP BY uid,nocApplicationId,applicantId,categoryId,applicationStatus,categoryName",nativeQuery = true)
    List<NocApplicationLabTestDTO> findNocApplicationAndLabTests(Long applicationStatus);

    /**
     * This method is for criteria search based on parameters
     * 
     * @author Md Mushfiq Fuad
     * @param categoryTypeSearch - categoryTypeSearch
     * @param categoryTypeId - categoryTypeId
     * @param statusSearch - statusSearch
     * @param applicationStatusId - Long
     * @param phoneNoSearch - phoneNoSearch
     * @param phoneNo - phoneNo
     * @param dateSearch - dateSearch
     * @param fromDate - fromDate
     * @param toDate - toDate
     * @param pageable - pageable
     * @return Page - Page
     */
    @Query("SELECT na FROM NocApplication na WHERE " +
            "(1 = :categoryTypeSearch OR na.nocCategory.id = :categoryTypeId) " + 
            "AND (1 = :statusSearch OR na.licenseNocStatus.id = :applicationStatusId) " + 
            "AND (1 = :phoneNoSearch OR na.phoneNo = :phoneNo) " 
            + 
            "AND (1 = :dateSearch OR na.createdAt >= :fromDate AND na.createdAt <= :toDate)"
            )
    Page<NocApplication> getAllNocApplicationByCriteria(
            @Param("categoryTypeSearch") int categoryTypeSearch, @Param("categoryTypeId") Long categoryTypeId,
            @Param("statusSearch") int statusSearch, @Param("applicationStatusId") Long applicationStatusId,
            @Param("phoneNoSearch") int phoneNoSearch, @Param("phoneNo") String phoneNo,
            @Param("dateSearch") int dateSearch, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, 
            Pageable pageable);

    /**
     * This method is to get count of element in criteria search based on parameters
     * 
     * @author Md Mushfiq Fuad
     * @param categoryTypeSearch - categoryTypeSearch
     * @param categoryTypeId - categoryTypeId
     * @param statusSearch - statusSearch
     * @param applicationStatusId - Long
     * @param phoneNoSearch - phoneNoSearch
     * @param phoneNo - phoneNo
     * @param dateSearch - dateSearch
     * @param fromDate - fromDate
     * @param toDate - toDate
     * @return Long - Long
     */
    @Query("SELECT COUNT(na) FROM NocApplication na WHERE " +
            "(1 = :categoryTypeSearch OR na.nocCategory.id = :categoryTypeId) " + 
            "AND (1 = :statusSearch OR na.licenseNocStatus.id = :applicationStatusId) " + 
            "AND (1 = :phoneNoSearch OR na.phoneNo = :phoneNo) " 
            + 
            "AND (1 = :dateSearch OR na.createdAt >= :fromDate AND na.createdAt <= :toDate)"
            )
    Long countApplicationByCriteria(
            @Param("categoryTypeSearch") int categoryTypeSearch, @Param("categoryTypeId") Long categoryTypeId,
            @Param("statusSearch") int statusSearch, @Param("applicationStatusId") Long applicationStatusId,
            @Param("phoneNoSearch") int phoneNoSearch, @Param("phoneNo") String phoneNo,
            @Param("dateSearch") int dateSearch, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    
    @Query("SELECT na FROM NocApplication na WHERE na.nocCategory.departmentId = :departmentId")
    Page<NocApplication> getAllNocApplicationByDepartment(@Param("departmentId") Long departmentId, Pageable pageable);
    
    @Query("SELECT COUNT(na) FROM NocApplication na WHERE na.nocCategory.departmentId = :departmentId")
    Long countApplicationByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT COUNT(na) FROM NocApplication na WHERE na.applicantId = :id")
    Long countNocApplicationByApplicantId(@Param("id") Long id);
    
    List<NocApplication> findByNocCategoryId(Long categoryId);

    Long countByLicenseNocStatus(LicenseNocStatus hasLicenseNocStatus);

    List<NocApplication> findByLicenseNocStatus(LicenseNocStatus hasLicenseNocStatus, Pageable pageable);
}
