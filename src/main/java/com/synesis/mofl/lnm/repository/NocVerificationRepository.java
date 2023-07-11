package com.synesis.mofl.lnm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.model.NocVerification;

/**
 * This repository is for Noc Verification
 * @author Md Mushfiq Fuad
 * @since 01 Feb, 2022
 *
 */
@Repository
public interface NocVerificationRepository extends JpaRepository<NocVerification, Long> {

    NocVerification findByNocApplication(NocApplication hasApplication);
    Optional<NocVerification> findByVerifiedBy(Long id);

}
