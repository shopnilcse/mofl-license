package com.synesis.mofl.lnm.helper;

import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.Certificate;
import com.synesis.mofl.lnm.payload.CertificateResponse;
/**
 * @author Md. Emran Hossain
 * @since 21 Mar, 2022
 * @version 1.1
 */
@Component
public class CertificateConverter {

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param certificate - entity
     * @return CertificateResponse - response
     * @since 21 Mar, 2022
     */
    public CertificateResponse convertEntityToRespons(Certificate certificate){
        CertificateResponse responseData = new CertificateResponse();

        responseData.setId(certificate.getId());
        responseData.setUserId(certificate.getUserId());
        responseData.setIssueDate(certificate.getIssueDate());
        responseData.setIssuedBy(certificate.getIssuedBy());
        responseData.setCertificateType(certificate.getCertificateType());
        responseData.setCertificateName(certificate.getCertificateName());
        responseData.setDescription(certificate.getDescription());
        responseData.setExpireDate(certificate.getExpireDate());
        responseData.setRenewalDate(certificate.getRenewalDate());
        responseData.setRejectionDate(certificate.getRejectionDate());
        responseData.setIsActive(certificate.getIsActive());
        responseData.setIsArchived(certificate.getIsArchived());
        responseData.setRemarks(certificate.getRemarks());

        return responseData;
    }
}
