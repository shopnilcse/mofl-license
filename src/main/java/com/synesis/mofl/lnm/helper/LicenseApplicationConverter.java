package com.synesis.mofl.lnm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.LicenseApplication;
import com.synesis.mofl.lnm.payload.LicenseApplicationResponse;
/**
 * @author Md. Emran Hossain
 * @since 06 Mar, 2022
 * @version 1.1
 */
@Component
public class LicenseApplicationConverter {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(LicenseApplicationConverter.class);

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param licenseApplication - entity
     * @return LicenseApplicationResponse - response
     * @throws Exception - exception 
     * @since 20 Mar, 2022
     */
    public LicenseApplicationResponse convertEntityToRespons(LicenseApplication licenseApplication) throws Exception{
        LicenseApplicationResponse responseData = new LicenseApplicationResponse();

        responseData.setId(licenseApplication.getId());
        responseData.setUid(licenseApplication.getUid());
        responseData.setApplicantId(licenseApplication.getApplicantId());
        responseData.setLicenseCategory(licenseApplication.getLicenseCategory());
        responseData.setLicenseNocStatus(licenseApplication.getLicenseNocStatus());
        responseData.setPhoneNo(licenseApplication.getPhoneNo());
        responseData.setPresentAddress(licenseApplication.getPresentAddress());
        responseData.setPermanentAddress(licenseApplication.getPermanentAddress());
        responseData.setNationality(licenseApplication.getNationality());
        responseData.setNameOfNominee(licenseApplication.getNameOfNominee());
        responseData.setNomineeNationality(licenseApplication.getNomineeNationality());
        responseData.setRelationWithNominee(licenseApplication.getRelationWithNominee());
        responseData.setNomineeSignature(licenseApplication.getNomineeSignature());
        responseData.setNomineePermanentAddress(licenseApplication.getNomineePermanentAddress());
        responseData.setDynamicFields(licenseApplication.getDynamicFields());
        responseData.setCreatedAt(DateHelper.convertDateToLocalDate(licenseApplication.getCreatedAt()));

        return responseData;
    }
}
