package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingRequest;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingResponse;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.repository.LicenseNocPaymentSettingRepository;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;

/**
 * @author Md Mushfiq Fuad
 * @since 30 Jan, 2022
 *
 */
@ContextConfiguration(classes = {LicenseNocPaymentSettingService.class})
@ExtendWith(SpringExtension.class)
class LicenseNocPaymentSettingServiceTest {

    @Autowired
    private LicenseNocPaymentSettingService licenseNocPaymentSettingService;

    @MockBean
    private LicenseNocPaymentSettingRepository licenseNocPaymentSettingRepository;

    @MockBean
    private LicenseCategoryRepository licenseCategoryRepository;

    @MockBean
    private NocCategoryRepository nocCategoryRepository;

    @Test
    void testGetAllLicenseNocPaymentSettings() {
        PageImpl<LicenseNocPaymentSetting> pageImpl = new PageImpl<>(new ArrayList<>());
        when(this.licenseNocPaymentSettingRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<LicenseNocPaymentSetting> actualAllLicenseNocPaymentSettings = this.licenseNocPaymentSettingService
                .getAllLicenseNocPaymentSettings(null);

        assertSame(pageImpl, actualAllLicenseNocPaymentSettings);
        assertTrue(actualAllLicenseNocPaymentSettings.toList().isEmpty());
        verify(this.licenseNocPaymentSettingRepository).findAll((Pageable) any());
    }

    @Test
    void testGetLicenseNocPaymentSettingById() {
        LicenseNocPaymentSetting licenseNocPaymentSetting = TestMockData.getLicenseNocPaymentSetting();

        Optional<LicenseNocPaymentSetting> ofResult = Optional.of(licenseNocPaymentSetting);
        when(this.licenseNocPaymentSettingRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(licenseNocPaymentSetting, this.licenseNocPaymentSettingService.getLicenseNocPaymentSettingById(123L));
        verify(this.licenseNocPaymentSettingRepository).findById((Long) any());
    }

    @Test
    void testGetLicenseNocPaymentSettingDetail() {
        LicenseNocPaymentSetting licenseNocPaymentSetting = TestMockData.getLicenseNocPaymentSetting();

        Optional<LicenseNocPaymentSetting> ofResult = Optional.of(licenseNocPaymentSetting);
        when(this.licenseNocPaymentSettingRepository.findById((Long) any())).thenReturn(ofResult);
        LicenseNocPaymentSettingResponse actualLicenseNocPaymentSettingDetail = this.licenseNocPaymentSettingService
                .getLicenseNocPaymentSettingDetail(123L);

        assertEquals(10.0, actualLicenseNocPaymentSettingDetail.getApplicationFee().doubleValue());
        assertEquals(10.0, actualLicenseNocPaymentSettingDetail.getVerificationFee().doubleValue());
        assertEquals(123L, actualLicenseNocPaymentSettingDetail.getId().longValue());
        assertTrue(actualLicenseNocPaymentSettingDetail.getHasVerificationFee());
        assertTrue(actualLicenseNocPaymentSettingDetail.getHasLabtestFee());
        assertTrue(actualLicenseNocPaymentSettingDetail.getHasCertificateFee());
        assertTrue(actualLicenseNocPaymentSettingDetail.getHasApplicationFee());
        assertEquals(10.0, actualLicenseNocPaymentSettingDetail.getCertificateFee().doubleValue());
        assertEquals("Base Type", actualLicenseNocPaymentSettingDetail.getBaseType());
        verify(this.licenseNocPaymentSettingRepository).findById((Long) any());
    }

    @Test
    void testSaveLicenseNocPaymentSetting() throws Exception {
        LicenseNocPaymentSetting licenseNocPaymentSetting = TestMockData.getLicenseNocPaymentSetting();
        LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest = TestMockData.getLicenseNocPaymentSettingRequest();

        when(this.licenseNocPaymentSettingRepository.save((LicenseNocPaymentSetting) any())).thenReturn(licenseNocPaymentSetting);

        assertSame(licenseNocPaymentSetting, this.licenseNocPaymentSettingService.saveLicenseNocPaymentSetting(licenseNocPaymentSettingRequest));
        verify(this.licenseNocPaymentSettingRepository).save((LicenseNocPaymentSetting) any());
    }

    @Test
    void testUpdateLicenseNocPaymentSetting() throws Exception {
        LicenseNocPaymentSetting licenseNocPaymentSetting = TestMockData.getLicenseNocPaymentSetting();
        LicenseNocPaymentSetting licenseNocPaymentSetting1 = TestMockData.getLicenseNocPaymentSetting();
        LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest = TestMockData.getLicenseNocPaymentSettingRequest();

        Optional<LicenseNocPaymentSetting> ofResult = Optional.of(licenseNocPaymentSetting);
        when(this.licenseNocPaymentSettingRepository.save((LicenseNocPaymentSetting) any()))
                .thenReturn(licenseNocPaymentSetting1);
        when(this.licenseNocPaymentSettingRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(licenseNocPaymentSetting1,
                this.licenseNocPaymentSettingService.updateLicenseNocPaymentSetting(licenseNocPaymentSettingRequest));
        verify(this.licenseNocPaymentSettingRepository).save((LicenseNocPaymentSetting) any());
        verify(this.licenseNocPaymentSettingRepository).findById((Long) any());
    }

    @Test
    void testUpdateLicenseNocPaymentSetting2() throws Exception {
        LicenseNocPaymentSetting licenseNocPaymentSetting = TestMockData.getLicenseNocPaymentSetting();
        LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest = TestMockData.getLicenseNocPaymentSettingRequest();

        when(this.licenseNocPaymentSettingRepository.save((LicenseNocPaymentSetting) any()))
                .thenReturn(licenseNocPaymentSetting);
        when(this.licenseNocPaymentSettingRepository.findById((Long) any()))
                .thenReturn(Optional.empty());

        assertNull(this.licenseNocPaymentSettingService.updateLicenseNocPaymentSetting(licenseNocPaymentSettingRequest));
        verify(this.licenseNocPaymentSettingRepository).findById((Long) any());
    }
}
