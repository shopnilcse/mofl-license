package com.synesis.mofl.lnm.payload;

/**
 * @author S M Abdul Kader
 * @since 15 Mar, 2022
 *
 */
public interface NocApplicationLabTestDTO {
    String getUid();
    Long getApplicantId();
    Long getNocApplicationId();
    Long getCategoryId();
    String getCategoryName();
    String getLabTestName();
    String getLabTestId();
}
