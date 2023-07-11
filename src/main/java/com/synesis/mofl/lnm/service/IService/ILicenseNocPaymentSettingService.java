package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingRequest;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingResponse;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 * @version 1.1
 */
public interface ILicenseNocPaymentSettingService {

    public Page<LicenseNocPaymentSetting> getAllLicenseNocPaymentSettings(Pageable pageable);
    public Page<LicenseNocPaymentSettingResponse> getAllLicensePaymentSettings(Pageable pageable);
    public Page<LicenseNocPaymentSettingResponse> getAllNocPaymentSettings(Pageable pageable);
    public LicenseNocPaymentSetting getLicenseNocPaymentSettingById(Long id);
    public LicenseNocPaymentSettingResponse getLicenseNocPaymentSettingDetail(Long id);
    public LicenseNocPaymentSetting saveLicenseNocPaymentSetting(LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) throws Exception;
    public LicenseNocPaymentSetting updateLicenseNocPaymentSetting (LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) throws Exception;
    Long countByBaseTypeIgnoreCase(String type);
    public LicenseNocPaymentSetting hasPaymentSetting(String type, Long categoryId);
}
