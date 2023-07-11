package com.synesis.mofl.lnm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.NocApplication;
import com.synesis.mofl.lnm.payload.NocApplicationResponse;
/**
 * @author Md. Emran Hossain
 * @since 20 Mar, 2022
 * @version 1.1
 */
@Component
public class NocApplicationConverter {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(NocApplicationConverter.class);

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param nocApplication - entity
     * @throws Exception - Exception
     * @return nocApplicationResponse - response
     * @since 20 Mar, 2022
     */
    public NocApplicationResponse convertEntityToRespons(NocApplication nocApplication) throws Exception {
        NocApplicationResponse responseData = new NocApplicationResponse();

        responseData.setId(nocApplication.getId());
        responseData.setUid(nocApplication.getUid());
        responseData.setApplicantId(nocApplication.getApplicantId());
        responseData.setNocCategory(nocApplication.getNocCategory());
        responseData.setLicenseNocStatus(nocApplication.getLicenseNocStatus());
        responseData.setPhoneNo(nocApplication.getPhoneNo());
        responseData.setPresentAddress(nocApplication.getPresentAddress());
        responseData.setPermanentAddress(nocApplication.getPermanentAddress());
        responseData.setNationality(nocApplication.getNationality());
        responseData.setNameOfNominee(nocApplication.getNameOfNominee());
        responseData.setNomineeNationality(nocApplication.getNomineeNationality());
        responseData.setRelationWithNominee(nocApplication.getRelationWithNominee());
        responseData.setNomineeSignature(nocApplication.getNomineeSignature());
        responseData.setNomineePermanentAddress(nocApplication.getNomineePermanentAddress());
        responseData.setDynamicFields(nocApplication.getDynamicFields());
        responseData.setCreatedAt(DateHelper.convertDateToLocalDate(nocApplication.getCreatedAt()));

        return responseData;
    }
}
