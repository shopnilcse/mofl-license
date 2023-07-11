package com.synesis.mofl.lnm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.repository.LicenseNocStatusRepository;
import com.synesis.mofl.lnm.service.IService.ILicenseNocStatusService;
/**
 * This service is to provide license category api
 *
 * @author Md. Emran Hossain
 * @since 23 Mar, 2022
 * @version 1.1
 */
@Service
public class LicenseNocStatusService implements ILicenseNocStatusService {

    @Autowired
    private LicenseNocStatusRepository licenseNocStatusRepository;

    /**
     * This api is for get all License Categories
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return Page - Page
     * @since 23 Mar, 2022
     */
    @Override
    public Page<LicenseNocStatus> getAllLicenseNocStatus(Pageable pageable) throws Exception {
        return licenseNocStatusRepository.findAll(pageable);
    }

    /**
     * This api is for get License Noc Status by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return LicenseNocStatus - Model
     * @since 23 Mar, 2022
     */
    @Override
    public LicenseNocStatus getLicenseNocStatusById(Long id) throws Exception {
        return licenseNocStatusRepository.findById(id).orElse(null);
    }
}
