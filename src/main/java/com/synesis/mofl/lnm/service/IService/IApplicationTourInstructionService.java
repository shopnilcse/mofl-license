package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.synesis.mofl.lnm.model.ApplicationTourInstruction;
import com.synesis.mofl.lnm.payload.ApplicationTourInstructionRequest;

/**
 * @author Md Mushfiq Fuad
 * @since 11 Apr, 2022
 *
 */
public interface IApplicationTourInstructionService {

    Page<ApplicationTourInstruction> getAllApplicationTourInstruction(Pageable pageable);
    ApplicationTourInstruction getApplicationTourInstructionById(Long id);
    ApplicationTourInstruction getApplicationTourInstructionByUserId() throws Exception;
    ApplicationTourInstruction saveApplicationTourInstruction(ApplicationTourInstructionRequest applicationTourInstructionRequest) throws Exception;
    ApplicationTourInstruction updateApplicationTourInstruction(ApplicationTourInstructionRequest applicationTourInstructionRequest) throws Exception;
}
