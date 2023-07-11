package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.synesis.mofl.lnm.helper.AddressConverter;
import com.synesis.mofl.lnm.helper.CommonMockData;
import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.model.District;
import com.synesis.mofl.lnm.model.Division;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.model.Upazila;
import com.synesis.mofl.lnm.payload.LicenseApplicationPayload;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;
import com.synesis.mofl.lnm.repository.DistrictRepository;
import com.synesis.mofl.lnm.repository.DivisionRepository;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.repository.LicenseNocAddressRepository;
import com.synesis.mofl.lnm.repository.UpazilaRepository;
import com.synesis.mofl.lnm.security.UserPrincipal;
/**
*
* @author Md. Emran Hossain
* @since 24 Feb, 2022
*/
@SpringBootTest
class LicenseApplicationServiceTest {

    @Mock
    private LicenseApplicationRepository licenseApplicationRepository;

    @InjectMocks
    private LicenseApplicationService licenseApplicationService;

    @Mock
    private LicenseCategoryRepository licenseCategoryRepository;

    @Mock
    private LicenseNocAddressRepository licenseNocAddressRepository;

    @Mock
    private AddressConverter addressConverter;

    @Mock
    private DivisionRepository divisionRepository;

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private UpazilaRepository upazilaRepository;

    /**
     * This test for get all License Application
     *
     * @author Md. Emran Hossain
     * @since 24 Feb, 2022
     */
    @Disabled
    @Test
    void testGetAllLicenseApplication() throws Exception {
        PageImpl<LicenseApplication> pageImpl = new PageImpl<>(new ArrayList<>());

        when(this.licenseApplicationRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<LicenseApplicationResponse> actualAllLicenseApplication = this.licenseApplicationService.getAllLicenseApplication(null);

        assertSame(pageImpl, actualAllLicenseApplication);
        assertTrue(actualAllLicenseApplication.toList().isEmpty());
        verify(this.licenseApplicationRepository).findAll((Pageable) any());
    }

    /**
     * This test for get License Application by id
     *
     * @author Md. Emran Hossain
     * @since 24 Feb, 2022
     */
    @Disabled
    @Test
    void testGetLicenseApplicationById() throws Exception {
        Department department = TestMockData.getDepartment();
        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);

        Optional<LicenseApplication> ofResult = Optional.of(licenseApplication);
        when(this.licenseApplicationRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(licenseApplication, this.licenseApplicationService.getLicenseApplicationById(123L));
        verify(this.licenseApplicationRepository).findById((Long) any());
    }

    /**
     * This test for get License Application by Applicant id
     *
     * @author Md. Emran Hossain
     * @throws Exception 
     * @since 24 Feb, 2022
     */
    @Disabled
    @Test
    void testGetLicenseApplicationByApplicantId() throws Exception {
//        PageImpl<LicenseApplication> pageImpl = new PageImpl<>(new ArrayList<>());
//        when(this.licenseApplicationRepository.findByApplicantId((Long) any(), (Pageable) any())).thenReturn(pageImpl);
//        Page<LicenseApplication> actualLicenseApplicationByApplicantId = this.licenseApplicationService.getLicenseApplicationByApplicantId(123L, null);
//
//        assertSame(pageImpl, actualLicenseApplicationByApplicantId);
//        assertTrue(actualLicenseApplicationByApplicantId.toList().isEmpty());
//        verify(this.licenseApplicationRepository).findByApplicantId((Long) any(), (Pageable) any());
    }

    /**
     * This test for get all Approved License Application
     *
     * @author Md. Emran Hossain
     * @since 08 Mar, 2022
     */
    @Disabled
    @Test
    void testGetAllApprovedLicenseApplication() throws Exception {
//        PageImpl<LicenseApplication> pageImpl = new PageImpl<>(new ArrayList<>());
//
//        when(this.licenseApplicationRepository.findByApplicationStatusIgnoreCase((String) any(),
//                (Pageable) any())).thenReturn(pageImpl);
//        Page<LicenseApplication> actualAllApprovedLicenseApplication = this.licenseApplicationService.getAllApprovedLicenseApplication(null);
//
//        assertSame(pageImpl, actualAllApprovedLicenseApplication);
//        assertTrue(actualAllApprovedLicenseApplication.toList().isEmpty());
//        verify(this.licenseApplicationRepository).findByApplicationStatusIgnoreCase((String) any(), (Pageable) any());
    }

    /**
     * This test for save License Application
     *
     * @author Md. Emran Hossain
     * @since 24 Feb, 2022
     */
    @Disabled
    @Test
    void testSaveLicenseApplication() throws Exception {
        Department department = TestMockData.getDepartment();
        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
        Department department1 = TestMockData.getDepartment();
        LicenseCategory licenseCategory1 = TestMockData.getLicenseCategory(department1);
        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory1);
        LicenseApplicationPayload licenseApplicationPayload = TestMockData.getLicenseApplicationPayload();
        LicenseNocAddress licenseNocAddress = TestMockData.getAddress();

        Division division = CommonMockData.getDivision();
        District district = CommonMockData.getDistrict(division);
        Upazila upzila = CommonMockData.getUpazila(district);

        // Mocking User Principal, Authentication & SecurityContex
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);

        Optional<LicenseCategory> ofResult = Optional.of(licenseCategory);
        when(this.licenseCategoryRepository.findById((Long) any())).thenReturn(ofResult);

        when(divisionRepository.findById((Long) any())).thenReturn(Optional.of(division));
        when(districtRepository.findById((Long) any())).thenReturn(Optional.of(district));
        when(upazilaRepository.findById((Long) any())).thenReturn(Optional.of(upzila));

        when(this.licenseNocAddressRepository.save((LicenseNocAddress) any())).thenReturn(licenseNocAddress);
        when(this.licenseApplicationRepository.save((LicenseApplication) any())).thenReturn(licenseApplication);

        assertSame(licenseApplication, this.licenseApplicationService.saveLicenseApplication(licenseApplicationPayload));
        verify(this.licenseCategoryRepository).findById((Long) any());
        verify(this.licenseApplicationRepository).save((LicenseApplication) any());
    }
}
