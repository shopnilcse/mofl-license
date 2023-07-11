package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.payload.CertificatePayload;
import com.synesis.mofl.lnm.repository.CertificateRepository;
import com.synesis.mofl.lnm.repository.LicenseApplicationRepository;
import com.synesis.mofl.lnm.security.UserPrincipal;
/**
 *
 * @author Md. Emran Hossain
 * @since 25 Jan, 2022
 */
@SpringBootTest
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private LicenseApplicationRepository licenseApplicationRepository;

    @InjectMocks
    private CertificateService certificateService;

    /**
     * This test for get all Certificate
     *
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     */
    @Disabled
    @Test
    void testGetAllCertificate() throws Exception {
        PageImpl<Certificate> pageImpl = new PageImpl<>(new ArrayList<>());
        when(this.certificateRepository.findAll((Pageable) any())).thenReturn(pageImpl);
//        Page<CertificateR> actualAllCertificate = this.certificateService.getAllCertificate(null);
//        assertSame(pageImpl, actualAllCertificate);
//        assertTrue(actualAllCertificate.toList().isEmpty());
        verify(this.certificateRepository).findAll((Pageable) any());
    }

    /**
     * This test for get Certificate by id
     *
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     */
    @Disabled
    @Test
    void testGetCertificateById() throws Exception {
        Certificate certificate = TestMockData.getCertificate();

        Optional<Certificate> ofResult = Optional.of(certificate);
        when(this.certificateRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(certificate, this.certificateService.getCertificateById(123L));
        verify(this.certificateRepository).findById((Long) any());
    }
    
    /**
     * This test for get Certificate by User ID
     * 
     * @author Md Mushfiq Fuad
     * @since 08 Mar, 2022
     */
    @Disabled
    @Test
    void testGetAllCertificateByUserId() throws Exception {
        // Mocking User Principal, Authentication & SecurityContex
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);
        
//        PageImpl<Certificate> pageImpl = new PageImpl<>(new ArrayList<>());
//        when(this.certificateRepository.findAllByUserId((Long) any(), (Pageable) any())).thenReturn(pageImpl);
//        Page<Certificate> actualAllCertificate = this.certificateService.getAllCertificateByUserId(null);
//        assertSame(pageImpl, actualAllCertificate);
//        assertTrue(actualAllCertificate.toList().isEmpty());
//        verify(this.certificateRepository).findAllByUserId((Long) any(), (Pageable) any());
        
    }

    /**
     * This test for save Certificate
     *
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     */
    @Disabled
    @Test
    void testSaveCertificate() throws Exception {
        Certificate certificate = TestMockData.getCertificate();
        CertificatePayload certificatePayload = TestMockData.getCertificatePayload();
        Department department = TestMockData.getDepartment();
        LicenseCategory licenseCategory = TestMockData.getLicenseCategory(department);
        LicenseApplication licenseApplication = TestMockData.getLicenseApplication(licenseCategory);

        // Mocking User Principal, Authentication & SecurityContex
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);
        
        when(licenseApplicationRepository.findById((Long) any())).thenReturn(Optional.of(licenseApplication));

        when(this.certificateRepository.save((Certificate) any())).thenReturn(certificate);
        assertSame(certificate, this.certificateService.saveCertificate(certificatePayload));
        verify(this.certificateRepository).save((Certificate) any());
    }

    /**
     * This test for update Certificate
     *
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     */
    @Disabled
    @Test
    void testUpdateCertificate() throws Exception {
        Certificate certificate = TestMockData.getCertificate();
        Certificate certificate1 = TestMockData.getCertificate();
        CertificatePayload certificatePayload = TestMockData.getCertificatePayload();

        Optional<Certificate> ofResult = Optional.of(certificate);
        when(this.certificateRepository.save((Certificate) any())).thenReturn(certificate1);
        when(this.certificateRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(certificate1, this.certificateService.updateCertificate(certificatePayload));
        verify(this.certificateRepository).findById((Long) any());
        verify(this.certificateRepository).save((Certificate) any());
    }

    /**
     * This test for renew Certificate
     *
     * @author Md. Emran Hossain
     * @since 25 Jan, 2022
     */
    @Disabled
    @Test
    void testRenewCertificate() throws Exception {
        Certificate certificate = TestMockData.getCertificate();
        Certificate certificate1 = TestMockData.getCertificate();
        CertificatePayload certificatePayload = TestMockData.getCertificatePayload();

        Optional<Certificate> ofResult = Optional.of(certificate);
        when(this.certificateRepository.save((Certificate) any())).thenReturn(certificate1);
        when(this.certificateRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(certificate1, this.certificateService.renewCertificate(certificatePayload));
        verify(this.certificateRepository).findById((Long) any());
        verify(this.certificateRepository).save((Certificate) any());
    }
}
