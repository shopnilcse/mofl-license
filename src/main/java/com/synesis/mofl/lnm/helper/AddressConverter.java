package com.synesis.mofl.lnm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synesis.mofl.lnm.model.LicenseNocAddress;
import com.synesis.mofl.lnm.payload.LicenseNocAddressRequest;
/**
 * @author Md. Emran Hossain
 * @since 06 Mar, 2022
 * @version 1.1
 */
@Component
public class AddressConverter {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AddressConverter.class);

    /**
     * This method convert payload to entity
     * 
     * @author Md. Emran Hossain
     * @param licenseNocAddressRequest - payload
     * @return licenseNocAddress - Model
     * @since 24 Feb, 2022
     */
    public LicenseNocAddress convertPayloadToEntity(LicenseNocAddressRequest licenseNocAddressRequest){
        LicenseNocAddress licenseNocAddress = new LicenseNocAddress();

        licenseNocAddress.getDivision().setId(licenseNocAddressRequest.getDivisionId());
        licenseNocAddress.getDistrict().setId(licenseNocAddressRequest.getDistrictId());
        licenseNocAddress.getUpazila().setId(licenseNocAddressRequest.getUpazilaId());
        licenseNocAddress.setAddressDetails(licenseNocAddressRequest.getAddressDetails());

        return licenseNocAddress;
    }
}
