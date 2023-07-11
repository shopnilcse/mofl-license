package com.synesis.mofl.lnm.payload;

import com.synesis.mofl.lnm.model.LabTest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author S M Abdul Kader
 * @since 14 Mar, 2022
 *
 */
@Getter
@Setter
public class NocApplicationLabTestPayload {
    private Long id;
    private Long labTestId;
    private Long nocApplicationId;
    private Long nocCategoryId;
    private String uid;
    private Long applicantId;
    private LabTest labTest;

}
