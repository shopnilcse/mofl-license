//package com.synesis.mofl.lnm.service;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.synesis.mofl.lnm.helper.TestMockData;
//import com.synesis.mofl.lnm.model.Department;
//import com.synesis.mofl.lnm.model.LicenseApplication;
//import com.synesis.mofl.lnm.model.LicenseCategory;
//import com.synesis.mofl.lnm.model.LicenseVerification;
//import com.synesis.mofl.lnm.payload.LicenseVerificationPayload;
//import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
//import com.synesis.mofl.lnm.repository.LicenseVerificationRepository;
//import com.synesis.mofl.lnm.security.UserPrincipal;
//
///**
// *
// * @author Md. Emran Hossain
// * @since 01 Feb, 2022
// */
//@ContextConfiguration(classes = {LicenseVerificationService.class})
//@ExtendWith(SpringExtension.class)
//class LicenseVerificationServiceTest {
//
//    @MockBean
//    private LicenseApplicationRepository licenseApplicationRepository;
//
//    @MockBean
//    private Authentication authentication;
//
//    @MockBean
//    private LicenseVerificationRepository licenseVerificationRepository;
//
//    @Autowired
//    private LicenseVerificationService licenseVerificationService;
//
//    @Disabled
//    @Test
//    void testGetLicenseVerificationById() throws Exception {
//        Department department = TestMockData.getDepartment();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);
//        LicenseVerification licenseVerification = TestMockData.getLicenseVerification(licenseApplication);
//
//        Optional<LicenseVerification> ofResult = Optional.of(licenseVerification);
//        when(this.licenseVerificationRepository.findById((Long) any())).thenReturn(ofResult);
//        assertSame(licenseVerification, this.licenseVerificationService.getLicenseVerificationById(123L));
//        verify(this.licenseVerificationRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for get License Verification By app Id
//     *
//     * @author Md. Emran Hossain
//     * @since 01 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testGetLicenseVerificationByappId() throws Exception {
//        Department department = TestMockData.getDepartment();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);
//        LicenseVerification licenseVerification = TestMockData.getLicenseVerification(licenseApplication);
//        LicenseCategory licenseCategory1 = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication1 = TestMockData.getLicenseApplication(licenseCategory1);
//
//        when(this.licenseVerificationRepository.findByLicenseApplication((LicenseApplication) any()))
//                .thenReturn(licenseVerification);
//        Optional<LicenseApplication> ofResult = Optional.of(licenseApplication1);
//        when(this.licenseApplicationRepository.findById((Long) any())).thenReturn(ofResult);
//        assertSame(licenseVerification, this.licenseVerificationService.getLicenseVerificationByappId(123L));
//        verify(this.licenseVerificationRepository).findByLicenseApplication((LicenseApplication) any());
//        verify(this.licenseApplicationRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for get License Verification By user Id
//     *
//     * @author Md. Emran Hossain
//     * @since 01 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testGetLicenseVerificationByUserId() throws Exception {
//        Department department = TestMockData.getDepartment();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);
//        LicenseVerification licenseVerification = TestMockData.getLicenseVerification(licenseApplication);
//
//        when(this.licenseVerificationRepository.findByVerifiedBy((Long) any())).thenReturn(licenseVerification);
//        assertSame(licenseVerification, this.licenseVerificationService.getLicenseVerificationByUserId(1L));
//        verify(this.licenseVerificationRepository).findByVerifiedBy((Long) any());
//    }
//
//    /**
//     * This test for save License Verification
//     *
//     * @author Md. Emran Hossain
//     * @since 01 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testSaveLicenseVerification() throws Exception {
//        Department department = TestMockData.getDepartment();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);
//        LicenseVerification licenseVerification = TestMockData.getLicenseVerification(licenseApplication);
//        LicenseCategory licenseCategory1 = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication1 = TestMockData.getLicenseApplication(licenseCategory1);
//        LicenseVerificationPayload licenseVerificationPayload = TestMockData.getLicenseVerificationPayload();
//
//        // Mocking User Principal, Authentication & SecurityContex
//        UserPrincipal userPrincipal = mock(UserPrincipal.class);
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);
//
//        when(this.licenseVerificationRepository.save((LicenseVerification) any())).thenReturn(licenseVerification);
//        Optional<LicenseApplication> ofResult = Optional.of(licenseApplication1);
//        when(this.licenseApplicationRepository.findById((Long) any())).thenReturn(ofResult);
//
//        assertSame(licenseVerification, this.licenseVerificationService.saveLicenseVerification(licenseVerificationPayload));
//        verify(this.licenseVerificationRepository).save((LicenseVerification) any());
//        verify(this.licenseApplicationRepository).findById((Long) any());
//    }
//
//    /**
//     * This test for update License Verification
//     *
//     * @author Md. Emran Hossain
//     * @since 01 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testUpdateLicenseVerification() throws Exception {
//        Department department = TestMockData.getDepartment();
//        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);
//        LicenseVerification licenseVerification = TestMockData.getLicenseVerification(licenseApplication);
//        LicenseCategory licenseCategory1 = TestMockData.getLicenseCategory(department);
//        LicenseApplication licenseApplication1 = TestMockData.getLicenseApplication(licenseCategory1);
//        LicenseVerification licenseVerification1 = TestMockData.getLicenseVerification(licenseApplication1);
//        LicenseVerificationPayload licenseVerificationPayload = TestMockData.getLicenseVerificationPayload();
//
//        Optional<LicenseVerification> ofResult = Optional.of(licenseVerification);
//
//        when(this.licenseVerificationRepository.save((LicenseVerification) any())).thenReturn(licenseVerification1);
//        when(this.licenseVerificationRepository.findById((Long) any())).thenReturn(ofResult);
//
//        assertSame(licenseVerification1,
//                this.licenseVerificationService.updateLicenseVerification(licenseVerificationPayload));
//        verify(this.licenseVerificationRepository).findById((Long) any());
//        verify(this.licenseVerificationRepository).save((LicenseVerification) any());
//    }
//}
