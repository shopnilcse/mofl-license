package com.synesis.mofl.lnm.service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.payload.CertificatePayload;
import com.synesis.mofl.lnm.payload.CertificateResponse;

/**
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
public interface ICertificateService {

    Page<CertificateResponse> getAllCertificate(Pageable pageable) throws Exception;

    CertificateResponse getCertificateById(Long id) throws Exception;

    Page<CertificateResponse> getAllCertificateByUserId(Pageable pageable) throws Exception;

    Certificate saveCertificate(CertificatePayload certificatePayload) throws Exception;

    Certificate updateCertificate(CertificatePayload certificatePayload) throws Exception;

    Certificate renewCertificate(CertificatePayload certificatePayload) throws Exception;

    Page<CertificateResponse> getCertificates(String type, Pageable pageable) throws Exception;
    
    Page<CertificateResponse> getAllActiveCertificates(String type, Pageable pageable) throws Exception;

    Long countCertificateByType(String type);
    
    Certificate forceDeactiveCertificate(CertificatePayload certificatePayload) throws Exception;

    Long countAll() throws Exception;

    Long countByUserId() throws Exception;

    Long countByAllActiveCertificate(String type) throws Exception;
}
