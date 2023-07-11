package com.synesis.mofl.lnm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.Certificate;
/**
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByUserId(Long id, Pageable pageable);

    Long countByCertificateTypeIgnoreCase(String type);

    List<Certificate> findByCertificateTypeIgnoreCase(String type, Pageable pageable);

    Long countByUserId(Long userId);
    

    @Query("SELECT ce FROM Certificate ce WHERE " + 
           " (lower(ce.certificateType) = lower(:certificateType)) " + 
           "AND (ce.expireDate >= :toDate) " + 
           "AND (ce.isActive = :isActive)")
    Page<Certificate> findByCertificateTypeAndExpireDateAndIsActive(@Param("certificateType") String type, @Param("toDate") LocalDate toDate,
            @Param("isActive") Boolean isActive, Pageable pageable);


    @Query("SELECT COUNT(ce) FROM Certificate ce WHERE " + 
            " (ce.certificateType = :certificateType) " + 
            "AND (ce.expireDate >= :toDate) " + 
            "AND (ce.isActive = :isActive)")
    Long countCertificateTypeAndExpireDateAndIsActive(@Param("certificateType") String type, @Param("toDate") LocalDate toDate,
            @Param("isActive") Boolean isActive);
}