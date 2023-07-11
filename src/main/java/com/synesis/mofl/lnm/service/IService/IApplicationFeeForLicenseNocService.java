package com.synesis.mofl.lnm.service.IService;

import org.springframework.http.ResponseEntity;
/**
 * @author Md. Kamruzzaman
 * @since 25 March, 2022
 * @version 1.1
 */
public interface IApplicationFeeForLicenseNocService {

    ResponseEntity<?> getApplicationPaymentInfoByApplicationId(Long id, String type);
}
