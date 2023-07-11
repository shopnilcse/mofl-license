package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author Md. Emran Hossain
 * @since 24 Jan, 2022
 * @version 1.1
 */
@Getter
@Setter
public class LicenseCategoryRequest {

    private Long id;
    private String categoryName;
    private Long formSetupId;
    private Long departmentId;
    private Long expirationPeriod;
}
