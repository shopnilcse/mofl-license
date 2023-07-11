package com.synesis.mofl.lnm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;

/**
 * This repository is for License Noc Payment Settings
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 *
 */
@Repository
public interface LicenseNocPaymentSettingRepository extends JpaRepository<LicenseNocPaymentSetting, Long> {

    Page<LicenseNocPaymentSetting> findAllByBaseType(String baseType, Pageable pageable);
    Long countByBaseTypeIgnoreCase(String baseType);
    LicenseNocPaymentSetting findByBaseTypeIgnoreCaseAndCategoryId(String baseType, Long id);
}
