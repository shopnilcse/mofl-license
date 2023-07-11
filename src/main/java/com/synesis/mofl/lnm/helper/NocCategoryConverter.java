package com.synesis.mofl.lnm.helper;

import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.NocCategory;
import com.synesis.mofl.lnm.payload.NocCategoryResponse;
/**
 * @author Md. Emran Hossain
 * @since 22 Mar, 2022
 * @version 1.1
 */
@Component
public class NocCategoryConverter {

    /**
     * This method convert entity to response
     * 
     * @author Md. Emran Hossain
     * @param nocCategory - entity
     * @return nocCategoryResponse - response
     * @since 22 Mar, 2022
     */
    public NocCategoryResponse convertEntityToRespons(NocCategory nocCategory){
        NocCategoryResponse responseData = new NocCategoryResponse();

        responseData.setId(nocCategory.getId());
        responseData.setCategoryName(nocCategory.getCategoryName());
        responseData.setFormSetupId(nocCategory.getFormSetupId());
        responseData.setExpirationPeriod(nocCategory.getExpirationPeriod());

        return responseData;
    }
}
