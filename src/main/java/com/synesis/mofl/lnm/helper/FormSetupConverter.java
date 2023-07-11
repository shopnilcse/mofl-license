package com.synesis.mofl.lnm.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.FormSetup;
import com.synesis.mofl.lnm.payload.FormSetupResponse;
import com.synesis.mofl.lnm.repository.LicenseCategoryRepository;
import com.synesis.mofl.lnm.repository.NocCategoryRepository;

/**
 * @author Md. Mushfiq Fuad
 *
 */
@Component
public class FormSetupConverter {

    
    @Autowired
    private LicenseCategoryRepository licenseCategoryRepository;
    @Autowired
    private NocCategoryRepository nocCategoryRepository;
    
    /**
     * 
     * @param formSetup - model
     * @return FormSetupResponse - payload
     * @throws Exception - exception
     */
    public FormSetupResponse convertFormSetupResponse(FormSetup formSetup) throws Exception{
        FormSetupResponse formSetupResponse = new FormSetupResponse();
        formSetupResponse.setId(formSetup.getId());
        formSetupResponse.setBaseType(formSetup.getBaseType());
        if(formSetup.getBaseType().equalsIgnoreCase("license")) {
            formSetupResponse.setCategory(licenseCategoryRepository.findById(formSetup.getCategoryId()).orElseThrow());
        } else if (formSetup.getBaseType().equalsIgnoreCase("noc")) {
            formSetupResponse.setCategory(nocCategoryRepository.findById(formSetup.getCategoryId()).orElseThrow());
        }
        formSetupResponse.setHasApplicationPayment(formSetup.getHasApplicationPayment());
        formSetupResponse.setHasFieldVerification(formSetup.getHasFieldVerification());
        formSetupResponse.setHasLabTest(formSetup.getHasLabTest());
        formSetupResponse.setHasProcessPayment(formSetup.getHasProcessPayment());
        formSetupResponse.setActive(formSetup.isActive());
        return formSetupResponse;
    }
}
