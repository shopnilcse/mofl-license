package com.synesis.mofl.lnm.payload;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md Mushfiq Fuad
 * @since 24 Feb, 2022
 *
 */
@Getter
@Setter
public class NocApplicationRequest {
    private Long id;
    private String uid;
    private Long applicantId;
    private Long nocCategoryId;
    private String phoneNo;
    private LicenseNocAddressRequest presentAddress;
    private LicenseNocAddressRequest permanentAddress;
    private String nationality;

    @Nullable
    private String nameOfNominee;

    @Nullable
    private String nomineeNationality;

    @Nullable
    private String relationWithNominee;

    @Nullable
    private String nomineeSignature;

    @Nullable
    private LicenseNocAddressRequest nomineePermanentAddress;

    @Nullable
    private String otherFields;
    

}
