package com.synesis.mofl.lnm.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesis.mofl.lnm.helper.UserHelper;
import com.synesis.mofl.lnm.model.ApplicationTourInstruction;
import com.synesis.mofl.lnm.payload.ApplicationTourInstructionRequest;
import com.synesis.mofl.lnm.repository.ApplicationTourInstructionRepository;
import com.synesis.mofl.lnm.service.IService.IApplicationTourInstructionService;

/**
 * @author Md Mushfiq Fuad
 * @since 11 Apr, 2022
 *
 */
@Service
public class ApplicationTourInstructionService  implements IApplicationTourInstructionService{

    @Autowired
    ApplicationTourInstructionRepository applicationTourInstructionRepository;
    @Override
    public Page<ApplicationTourInstruction> getAllApplicationTourInstruction(Pageable pageable) {
        return applicationTourInstructionRepository.findAll(pageable);
    }

    @Override
    public ApplicationTourInstruction getApplicationTourInstructionById(Long id) {
        return applicationTourInstructionRepository.findById(id).orElse(null);
    }

    @Override
    public ApplicationTourInstruction getApplicationTourInstructionByUserId() throws Exception {
        return applicationTourInstructionRepository.findByUserId(UserHelper.getUserId());
    }

    @Transactional
    @Override
    public ApplicationTourInstruction saveApplicationTourInstruction(
            ApplicationTourInstructionRequest applicationTourInstructionRequest) throws Exception {
        ApplicationTourInstruction applicationTourInstruction = new ApplicationTourInstruction();
        if(this.getApplicationTourInstructionByUserId() != null) {
            throw new EntityExistsException();
        }
        applicationTourInstruction.setUserId(UserHelper.getUserId());
        applicationTourInstruction.setIsTourActive(true);
        applicationTourInstruction.setRemarks(applicationTourInstructionRequest.getRemarks());
        ApplicationTourInstruction savedApplicationTourInstruction = applicationTourInstructionRepository.save(applicationTourInstruction);
        return savedApplicationTourInstruction;
    }

    @Transactional
    @Override
    public ApplicationTourInstruction updateApplicationTourInstruction(ApplicationTourInstructionRequest applicationTourInstructionRequest) throws Exception {
        ApplicationTourInstruction existedApplicationTourInstruction = applicationTourInstructionRepository.findById(applicationTourInstructionRequest.getId()).orElseThrow(() -> new EntityNotFoundException());
        existedApplicationTourInstruction.setIsTourActive(false);
        existedApplicationTourInstruction.setRemarks(applicationTourInstructionRequest.getRemarks());
        ApplicationTourInstruction savedApplicationTourInstruction = applicationTourInstructionRepository.save(existedApplicationTourInstruction);
        return savedApplicationTourInstruction;
    }

}
