///**
// * 
// */
//package com.synesis.mofl.lnm.service;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.synesis.mofl.lnm.helper.AddressConverter;
//import com.synesis.mofl.lnm.helper.TestMockData;
//import com.synesis.mofl.lnm.model.Department;
//import com.synesis.mofl.lnm.model.LicenseNocAddress;
//import com.synesis.mofl.lnm.model.NocApplication;
//import com.synesis.mofl.lnm.model.NocCategory;
//import com.synesis.mofl.lnm.payload.LicenseNocAddressRequest;
//import com.synesis.mofl.lnm.payload.NocApplicationRequest;
//import com.synesis.mofl.lnm.repository.LicenseNocAddressRepository;
//import com.synesis.mofl.lnm.repository.NocApplicationRepository;
//import com.synesis.mofl.lnm.repository.NocCategoryRepository;
//import com.synesis.mofl.lnm.security.UserPrincipal;
//
///**
// * This class provides all the test cases for NocApplicationService class
// * 
// * @author Md Mushfiq Fuad
// * @since 27 Feb, 2022
// */
//@SpringBootTest
//class NocApplicationServiceTest {
//
//    @Mock
//    private NocApplicationRepository nocApplicationRepository;
//
//    @InjectMocks
//    private NocApplicationService nocApplicationService;
//
//    @Mock
//    private NocCategoryRepository nocCategoryRepository;
//
//    @Mock
//    private LicenseNocAddressRepository licenseNocAddressRepository;
//
//    @Mock
//    private AddressConverter addressConverter;
//    /**
//     * Test method for getAllNocApplication(Pageable pageable).
//     * 
//     * @author Md Mushfiq Fuad
//     * @since 27 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testGetAllNocApplication() {
//        PageImpl<NocApplication> pageImpl = new PageImpl<>(new ArrayList<>());
//
//        when(this.nocApplicationRepository.findAll((Pageable) any())).thenReturn(pageImpl);
//        Page<NocApplication> actualAllNocApplication = this.nocApplicationService.getAllNocApplication(null);
//
//        assertSame(pageImpl, actualAllNocApplication);
//        assertTrue(actualAllNocApplication.toList().isEmpty());
//        verify(this.nocApplicationRepository).findAll((Pageable) any());
//    }
//
//    /**
//     * Test method for getNocApplicationById(Long id).
//     * 
//     * @author Md Mushfiq Fuad
//     * @since 27 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testGetNocApplicationById() {
//        Department department = TestMockData.getDepartment();
//        NocCategory nocCategory = TestMockData.getNocCategory(department);
//        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
//
//        Optional<NocApplication> ofResult = Optional.of(nocApplication);
//        when(this.nocApplicationRepository.findById((Long) any())).thenReturn(ofResult);
//
//        assertSame(nocApplication, this.nocApplicationService.getNocApplicationById(123L));
//        verify(this.nocApplicationRepository).findById((Long) any());
//    }
//
//    /**
//     * Test method for getNocApplicationByApplicantId(Long id, Pageable pageable).
//     * 
//     * @author Md Mushfiq Fuad
//     * @since 27 Feb, 2022
//     */
//    @Disabled
//    @Test
//    void testGetNocApplicationByApplicantId() {
//        PageImpl<NocApplication> pageImpl = new PageImpl<>(new ArrayList<>());
//        when(this.nocApplicationRepository.findByApplicantId((Long) any(), (Pageable) any())).thenReturn(pageImpl);
//        Page<NocApplication> actualNocApplicationByApplicantId = this.nocApplicationService.getNocApplicationByApplicantId(123L, null);
//
//        assertSame(pageImpl, actualNocApplicationByApplicantId);
//        assertTrue(actualNocApplicationByApplicantId.toList().isEmpty());
//        verify(this.nocApplicationRepository).findByApplicantId((Long) any(), (Pageable) any());
//    }
//
//    /**
//     * This test for get all Approved Noc Application
//     *
//     * @author Md. Emran Hossain
//     * @since 08 Mar, 2022
//     */
//    @Disabled
//    @Test
//    void testGetAllApprovedNocApplication() throws Exception {
//        PageImpl<NocApplication> pageImpl = new PageImpl<>(new ArrayList<>());
//
//        when(this.nocApplicationRepository.findByApplicationStatusIgnoreCase((String) any(),
//                (Pageable) any())).thenReturn(pageImpl);
//        Page<NocApplication> actualAllApprovedNocApplication = this.nocApplicationService.getAllApprovedNocApplication(null);
//
//        assertSame(pageImpl, actualAllApprovedNocApplication);
//        assertTrue(actualAllApprovedNocApplication.toList().isEmpty());
//        verify(this.nocApplicationRepository).findByApplicationStatusIgnoreCase((String) any(), (Pageable) any());
//    }
//
//    /**
//     * Test method for saveNocApplication(NocApplicationRequest nocApplicationRequest).
//     * 
//     * 
//     * @author Md Mushfiq Fuad
//     * @since 27 Feb, 2022
//     * @throws Exception 
//     */
//    @Disabled
//    @Test
//    void testSaveNocApplication() throws Exception {
//        Department department = TestMockData.getDepartment();
//        NocCategory nocCategory = TestMockData.getNocCategory(department);
//        Department department1 = TestMockData.getDepartment();
//        NocCategory nocCategory1 = TestMockData.getNocCategory(department1);
//        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory1);
//        NocApplicationRequest nocApplicationRequest = TestMockData.getNocApplicationRequest();
//        LicenseNocAddress licenseNocAddress = TestMockData.getAddress();
//
//        // Mocking User Principal, Authentication & SecurityContex
//        UserPrincipal userPrincipal = mock(UserPrincipal.class);
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);
//
//        Optional<NocCategory> ofResult = Optional.of(nocCategory);
//        when(this.nocCategoryRepository.findById((Long) any())).thenReturn(ofResult);
//
//        when(addressConverter.convertPayloadToEntity((LicenseNocAddressRequest) any())).thenReturn(licenseNocAddress);
//
//        when(this.licenseNocAddressRepository.save((LicenseNocAddress) any())).thenReturn(licenseNocAddress);
//        when(this.nocApplicationRepository.save((NocApplication) any())).thenReturn(nocApplication);
//
//        assertSame(nocApplication, this.nocApplicationService.saveNocApplication(nocApplicationRequest));
//        verify(this.nocCategoryRepository).findById((Long) any());
//        verify(this.nocApplicationRepository).save((NocApplication) any());
//    }
//
//}
