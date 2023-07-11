package com.synesis.mofl.lnm.service.IService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.FormField;
import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.payload.FormSetupRequest;
import com.synesis.mofl.lnm.payload.FormSetupResponse;

/**
 * This interface provides all service level methods
 * 
 * @author Md Mushfiq Fuad
 * @since 04 Apr, 2022
 *
 */
public interface IFormSetupService {

    Page<FormSetupResponse> getAllFormSetup(Pageable pageable) throws Exception;
    FormSetupResponse getFormSetupResponseById(Long id) throws Exception;
    List<FormField> saveFormSetup(FormSetupRequest formSetupRequest) throws Exception;
    Page<FormSetupResponse> getAllFormSetupByType(String type, Pageable pageable) throws Exception;
    Long countAll();
    Long countByBaseType(String type);
    Boolean hasSetupExist(String type, Long categoryId);
    FormSetup updateIsActivate(Long id, boolean status) throws Exception;
}
