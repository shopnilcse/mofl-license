package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 10 Mar, 2022
 *
 */
@Getter
@Setter
public class LicenseCategoryResponse {

    private Long id;
    private String categoryName;
    private Long formSetupId;
    private Long departmentId;
    private String departmentName;
    private Long expirationPeriod;
}
