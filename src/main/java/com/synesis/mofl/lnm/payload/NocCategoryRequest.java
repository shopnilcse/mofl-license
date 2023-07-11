package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Jan, 2022
 *
 */
@Getter
@Setter
public class NocCategoryRequest {
    private Long id;
    private String categoryName;
    private Long formSetupId;
    private Long departmentId;
    private Long expirationPeriod;
}
