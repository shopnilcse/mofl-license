package com.synesis.mofl.lnm.service.IService;


import com.synesis.mofl.lnm.model.NocVerification;
import com.synesis.mofl.lnm.payload.NocVerificationRequest;
/**
 * @author Md Mushfiq Fuad
 * @since 01 Feb, 2022
 * @version 1.1
 */
public interface INocVerificationService {

    public NocVerification getNocVerificationById(Long id);
    public NocVerification saveNocVerification(NocVerificationRequest nocVerificationRequest) throws Exception;
    public NocVerification updateNocVerification(NocVerificationRequest nocVerificationRequest) throws Exception;
    public NocVerification getNocVerificationByAppId(Long id);
    public NocVerification getNocVerificationByVerifiedBy(Long id);
}
