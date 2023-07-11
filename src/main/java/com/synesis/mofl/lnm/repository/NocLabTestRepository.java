package com.synesis.mofl.lnm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.NocLabTest;

/**
 * This repository for NocLabTest
 * @author S M Abdul Kader
 * @since 22 Feb, 2022
 *
 */
@Repository
public interface NocLabTestRepository extends JpaRepository<NocLabTest, Long> {
    @Query(value = "SELECT nlt.* FROM noc_lab_tests nlt WHERE nlt.noc_category_id = ?1", nativeQuery = true)
    List<NocLabTest> findAllByCategoryId(Long id);
}
