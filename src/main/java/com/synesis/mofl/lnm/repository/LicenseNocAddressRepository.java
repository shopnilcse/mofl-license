package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.LicenseNocAddress;
/**
 * @author Md. Emran Hossain
 * @since 28 Feb, 2022
 * @version 1.1
 */
@Repository
public interface LicenseNocAddressRepository extends JpaRepository<LicenseNocAddress, Long> {

}