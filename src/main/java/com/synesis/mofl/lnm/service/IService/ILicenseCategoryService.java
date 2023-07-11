package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.payload.LicenseCategoryRequest;
import com.synesis.mofl.lnm.payload.LicenseCategoryResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
public interface ILicenseCategoryService {

    Page<LicenseCategory> getAllLicenseCategory(Pageable pageable) throws Exception;

    LicenseCategoryResponse getLicenseCategoryById(Long id) throws Exception;

    LicenseCategory saveLicenseCategory(LicenseCategoryRequest licenseCategoryRequest) throws Exception;

    LicenseCategory updateLicenseCategory(LicenseCategoryRequest licenseCategoryRequest) throws Exception;
}
