package com.synesis.mofl.lnm.payload;

import java.time.LocalDate;

import com.synesis.mofl.lnm.model.LicenseCategory;
import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.model.LicenseNocStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Md. Emran Hossain
 * @since 24 Feb, 2022
 * @version 1.1
 */
@Getter
@Setter
public class LicenseApplicationResponse {

    private Long id;
    private String uid;
    private Long applicantId;
    private String applicantName;
    private LicenseCategory licenseCategory;
    private LicenseNocStatus licenseNocStatus;

    private String phoneNo;
    private LicenseNocAddress presentAddress;
    private LicenseNocAddress permanentAddress;
    private String nationality;
    private String nameOfNominee;

    private String nomineeNationality;
    private String relationWithNominee;
    private String nomineeSignature;
    private LicenseNocAddress nomineePermanentAddress;
    private String dynamicFields;
    private LocalDate createdAt;
}
