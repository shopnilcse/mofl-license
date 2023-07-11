package com.synesis.mofl.lnm.helper;

import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.payload.LicenseCategoryResponse;
/**
 * @author Md. Emran Hossain
 * @since 21 Mar, 2022
 * @version 1.1
 */
@Component
public class LicenseCategoryConverter {

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param licenseCategory - entity
     * @return LicenseCategoryResponse - response
     * @since 21 Mar, 2022
     */
    public LicenseCategoryResponse convertEntityToRespons(LicenseCategory licenseCategory){
        LicenseCategoryResponse responseData = new LicenseCategoryResponse();

        responseData.setId(licenseCategory.getId());
        responseData.setCategoryName(licenseCategory.getCategoryName());
        responseData.setFormSetupId(licenseCategory.getFormSetupId());
        responseData.setExpirationPeriod(licenseCategory.getExpirationPeriod());

        return responseData;
    }
}
