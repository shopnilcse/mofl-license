package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.NocCategoryRequest;
import com.synesis.mofl.lnm.payload.NocCategoryResponse;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 * @version 1.1
 */
public interface INocCategoryService {

    public Page<NocCategory> getAllNocCategory(Pageable pageable);
    public NocCategoryResponse getNocCategoryById(Long id);
    public NocCategory saveNocCategory(NocCategoryRequest nocCategoryRequest) throws Exception;
    public NocCategory updateNocCategory (NocCategoryRequest nocCategoryRequest) throws Exception;
}
