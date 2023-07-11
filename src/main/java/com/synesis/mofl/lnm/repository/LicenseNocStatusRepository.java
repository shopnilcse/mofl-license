package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseNocStatus;
/**
 * @author Md. Emran Hossain
 * @since 23 Mar, 2022
 * @version 1.1
 */
@Repository
public interface LicenseNocStatusRepository extends JpaRepository<LicenseNocStatus, Long> {

}