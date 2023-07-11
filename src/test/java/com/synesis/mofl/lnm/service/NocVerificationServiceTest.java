package com.synesis.mofl.lnm.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.synesis.mofl.lnm.helper.TestMockData;
import com.synesis.mofl.lnm.model.Department;
import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.model.NocVerification;
import com.synesis.mofl.lnm.payload.NocVerificationRequest;
import com.synesis.mofl.lnm.repository.NocApplicationRepository;
import com.synesis.mofl.lnm.repository.NocVerificationRepository;
import com.synesis.mofl.lnm.security.UserPrincipal;

@ContextConfiguration(classes = {NocVerificationService.class})
@ExtendWith(SpringExtension.class)
class NocVerificationServiceTest {

    @MockBean
    private NocVerificationRepository nocVerificationRepository;

    @MockBean
    private NocApplicationRepository nocApplicationRepository;

    @Autowired
    private NocVerificationService nocVerificationService;

    /**
     * Test method for getNocVerificationById(Long id).
     */
    @Test
    void testGetNocVerificationById() {
        Department department = TestMockData.getDepartment();
        NocCategory nocCategory = TestMockData.getNocCategory(department);
        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
        NocVerification nocVerification = TestMockData.getNocVerification(nocApplication);

        Optional<NocVerification> ofResult = Optional.of(nocVerification);
        when(this.nocVerificationRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(nocVerification, this.nocVerificationService.getNocVerificationById(123L));
        verify(this.nocVerificationRepository).findById((Long) any());
    }

    /**
     * Test method for saveNocVerification(NocVerificationRequest nocVerificationRequest).
     * @throws Exception - Exception
     */
    @Test
    void testSaveNocVerification() throws Exception {
    	Department department = TestMockData.getDepartment();
        NocCategory nocCategory = TestMockData.getNocCategory(department);
        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
        NocVerification nocVerification = TestMockData.getNocVerification(nocApplication);
        NocCategory nocCategory1 = TestMockData.getNocCategory(department);
        NocApplication nocApplication1 = TestMockData.getNocApplication(nocCategory1);
        NocVerificationRequest nocVerificationRequest = TestMockData.getNocVerificationRequest();

        // mock UserPrincipal, Authentication, SecurityContext
        UserPrincipal userPrincipal = mock(UserPrincipal.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userPrincipal);

        when(this.nocVerificationRepository.save((NocVerification) any())).thenReturn(nocVerification);

        Optional<NocApplication> ofResult = Optional.of(nocApplication1);
        when(this.nocApplicationRepository.findById((Long) any())).thenReturn(ofResult);

        assertSame(nocVerification, this.nocVerificationService.saveNocVerification(nocVerificationRequest));
        verify(this.nocVerificationRepository).save((NocVerification) any());
        verify(this.nocApplicationRepository).findById((Long) any());
    }

    /**
     * Test method for updateNocVerification(NocVerificationRequest nocVerificationRequest).
     */
    @Test
    void testUpdateNocVerification() {
        Department department = TestMockData.getDepartment();
        NocCategory nocCategory = TestMockData.getNocCategory(department);
        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
        NocVerification nocVerification = TestMockData.getNocVerification(nocApplication);

        Optional<NocVerification> ofResult = Optional.of(nocVerification);
        when(this.nocVerificationRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(nocVerification, this.nocVerificationService.getNocVerificationById(123L));
        verify(this.nocVerificationRepository).findById((Long) any());
    }

    /**
     * Test method for getNocVerificationByAppId(Long applicationId).
     */
    @Test
    void testGetNocVerificationByAppId() {
        Department department = TestMockData.getDepartment();
        NocCategory nocCategory = TestMockData.getNocCategory(department);
        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
        NocVerification nocVerification = TestMockData.getNocVerification(nocApplication);
        NocCategory nocCategory1 = TestMockData.getNocCategory(department);
        NocApplication nocApplication1 = TestMockData.getNocApplication(nocCategory1);

        when(this.nocVerificationRepository.findByNocApplication((NocApplication) any()))
                .thenReturn(nocVerification);
        Optional<NocApplication> ofResult = Optional.of(nocApplication1);
        when(this.nocApplicationRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(nocVerification, this.nocVerificationService.getNocVerificationByAppId(123L));
        verify(this.nocVerificationRepository).findByNocApplication((NocApplication) any());
        verify(this.nocApplicationRepository).findById((Long) any());
    }

    /**
     * Test method for getNocVerificationByVerifiedBy(Long userId).
     */
    @Test
    void testGetNocVerificationByVerifiedBy() {
        Department department = TestMockData.getDepartment();
        NocCategory nocCategory = TestMockData.getNocCategory(department);
        NocApplication nocApplication = TestMockData.getNocApplication(nocCategory);
        NocVerification nocVerification = TestMockData.getNocVerification(nocApplication);
        Optional<NocVerification> nocVerification2 = Optional.of(nocVerification);

        when(this.nocVerificationRepository.findByVerifiedBy((Long) any())).thenReturn(nocVerification2);
        assertSame(nocVerification, this.nocVerificationService.getNocVerificationByVerifiedBy(1L));
        verify(this.nocVerificationRepository).findByVerifiedBy((Long) any());
    }
}
