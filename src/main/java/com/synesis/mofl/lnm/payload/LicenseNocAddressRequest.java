package com.synesis.mofl.lnm.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 24 Feb, 2022
 * @version 1.1
 */
@Getter
@Setter
public class LicenseNocAddressRequest {

    private Long id;

    @NotNull
    private Long divisionId;

    @NotNull
    private Long districtId;

    private Long upazilaId;
    private String addressDetails;
}
