package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synesis.mofl.lnm.model.NocApplicationProcessLog;
/**
 * This repository for Noc ApplicationP rocessLog
 * @author S M Abdul Kader
 * @since 16 Mar, 2022
 *
 */
@Repository
public interface NocApplicationProcessLogRepository extends JpaRepository<NocApplicationProcessLog, Long> {
}
