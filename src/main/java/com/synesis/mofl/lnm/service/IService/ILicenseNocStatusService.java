package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.LicenseNocStatus;

/**
 *
 * @author Md. Emran Hossain
 * @since 23 Mar, 2022
 * @version 1.1
 */
public interface ILicenseNocStatusService {

    Page<LicenseNocStatus> getAllLicenseNocStatus(Pageable pageable) throws Exception;

    LicenseNocStatus getLicenseNocStatusById(Long id) throws Exception;
}
