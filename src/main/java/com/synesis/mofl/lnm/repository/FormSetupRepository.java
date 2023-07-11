package com.synesis.mofl.lnm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.FormSetup;
/**
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Repository
public interface FormSetupRepository extends JpaRepository<FormSetup, Long> {

    FormSetup findByBaseTypeIgnoreCaseAndCategoryId(String baseType, Long categoryId);
    FormSetup findByBaseTypeIgnoreCaseAndCategoryIdAndIsActive(String baseType, Long id, boolean active);
    Page<FormSetup> findByBaseTypeIgnoreCase(String type, Pageable pageable);
    Long countByBaseTypeIgnoreCase(String type);
}