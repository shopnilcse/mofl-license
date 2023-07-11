package com.synesis.mofl.lnm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synesis.mofl.lnm.model.ApplicationTourInstruction;

/**
 * @author Md Mushfiq Fuad
 * @since 11 Apr, 2022
 *
 */
@Repository
public interface ApplicationTourInstructionRepository extends JpaRepository<ApplicationTourInstruction, Long>{

    ApplicationTourInstruction findByUserId(Long userId);
}
