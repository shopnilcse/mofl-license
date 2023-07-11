package com.synesis.mofl.lnm.payload;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 24 Feb, 2022
 * @version 1.1
 */
@Getter
@Setter
public class LicenseApplicationPayload {

    private Long id;
    private String uid;
    private Long applicantId;
    private Long licenseCategoryId;
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
