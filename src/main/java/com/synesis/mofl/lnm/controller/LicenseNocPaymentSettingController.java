package com.synesis.mofl.lnm.controller;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.LicenseNocPaymentSettingConstants;
import com.synesis.mofl.lnm.helper.message.LicenseNocPaymentMessageConstants;
import com.synesis.mofl.lnm.model.LicenseNocPaymentSetting;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingRequest;
import com.synesis.mofl.lnm.payload.LicenseNocPaymentSettingResponse;
import com.synesis.mofl.lnm.service.IService.ILicenseNocPaymentSettingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
/**
 * This controller is to provide all the License Noc Payment Setting related api's
 * @author Md Mushfiq Fuad
 * @since 30 Jan, 2022
 * @version 1.1
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping(LicenseNocPaymentSettingConstants.ROOT)
public class LicenseNocPaymentSettingController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseNocPaymentSettingController.class);
    
    @Autowired
    private ILicenseNocPaymentSettingService iLicenseNocPaymentSettingService;
    
    /**
     * This api is to get all License Noc Payment Settings
     * @author Md Mushfiq Fuad
     * @since 30 Jan, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get all License Noc Payment Settings", description = "Get all License Noc Payment Settings")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all License Noc Payment Settings.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Payment Settings failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.GET_ALL)
    public ResponseEntity<?> getAllLicenseNocPaymentSetting(Pageable pageable) {
    try {
        Page<LicenseNocPaymentSetting> allLicenseNocPaymentSetting = iLicenseNocPaymentSettingService.getAllLicenseNocPaymentSettings(pageable);
        if(ObjectUtils.isEmpty(allLicenseNocPaymentSetting)) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,null), HttpStatus.OK);
        }
        LOG.info("All License Noc Payment Setting retrieved");
        Long element = allLicenseNocPaymentSetting.getTotalElements();
        return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_EN, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_BN, allLicenseNocPaymentSetting.getContent(), element), HttpStatus.OK);
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    /**
     * This api is to get all License Payment Settings
     * 
     * @author Md Mushfiq Fuad
     * @since 20 Mar, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get all License Payment Settings", description = "Get all License Payment Settings")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all License Payment Settings.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Payment Settings failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.GET_ALL_BY_LICENSE)
    public ResponseEntity<?> getAllLicensePaymentSetting(Pageable pageable) {
    try {
        Page<LicenseNocPaymentSettingResponse> allLicensePaymentSetting = iLicenseNocPaymentSettingService.getAllLicensePaymentSettings(pageable);
        if(allLicensePaymentSetting.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,null), HttpStatus.OK);
        }
        LOG.info("All License Payment Setting retrieved");
        Long element = iLicenseNocPaymentSettingService.countByBaseTypeIgnoreCase("license".toLowerCase());
        return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_EN, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_BN, allLicensePaymentSetting.getContent(), element), HttpStatus.OK);
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    
    /**
     * This api is to get all Noc Payment Settings
     * 
     * @author Md Mushfiq Fuad
     * @since 20 Mar, 2022
     * @param pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get all Noc Payment Settings", description = "Get all Noc Payment Settings")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get all Noc Payment Settings.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Payment Settings failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.GET_ALL_BY_NOC)
    public ResponseEntity<?> getAllNocPaymentSetting(Pageable pageable) {
    try {
        Page<LicenseNocPaymentSettingResponse> allNocPaymentSetting = iLicenseNocPaymentSettingService.getAllNocPaymentSettings(pageable);
        if(allNocPaymentSetting.isEmpty()) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,null), HttpStatus.OK);
        }
        LOG.info("All Noc Payment Setting retrieved");
        Long element = iLicenseNocPaymentSettingService.countByBaseTypeIgnoreCase("noc".toLowerCase());
        return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_EN, LicenseNocPaymentMessageConstants.ALL_LICENSE_NOC_PAYMENT_BN, allNocPaymentSetting.getContent(), element), HttpStatus.OK);
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage() ,null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    /**
     * This api is to get License Noc Payment Setting by Id
     * @author Md Mushfiq Fuad
     * @since 30 Jan, 2022 
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get License Noc Payment Setting by id", description = "Get License Noc Payment Setting by id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get License Noc Payment Setting by id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Payment Setting failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.GET_BY_ID)
    public ResponseEntity<?> getLicenseNocPaymentSettingById(@PathVariable Long id) {
    try {
        LicenseNocPaymentSetting licenseNocPaymentSetting = iLicenseNocPaymentSettingService.getLicenseNocPaymentSettingById(id);
        if(ObjectUtils.isEmpty(licenseNocPaymentSetting)) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,0L), HttpStatus.OK);
        }
        LOG.info("License Noc Payment Setting retrieved");
        return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_BN, licenseNocPaymentSetting, 1L), HttpStatus.OK);
        
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    
    /**
     * This api is to get License Noc Payment Setting Details by id
     * @author Md Mushfiq Fuad
     * @since 22 Feb, 2022
     * @param id - Long id
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get License Noc Payment Setting Details by id", description = "Get License Noc Payment Setting Detail by id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get License Noc Payment Setting Detail  by id.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Payment Setting Detail  failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.GET_DETAIL_BY_ID)
    public ResponseEntity<?> getLicenseNocPaymentSettingDetailById(@PathVariable Long id) {
    try {
        LicenseNocPaymentSettingResponse licenseNocPaymentSettingResponse = iLicenseNocPaymentSettingService.getLicenseNocPaymentSettingDetail(id);
        if(ObjectUtils.isEmpty(licenseNocPaymentSettingResponse)) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,0L), HttpStatus.OK);
        }
        LOG.info("License Noc Payment Setting Detail retrieved");
        return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_BN, licenseNocPaymentSettingResponse, 1L), HttpStatus.OK);
    
    } catch(Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    /**
     * This api is to save License Noc Payment Setting in Database
     * @author Md Mushfiq Fuad
     * @since 30 Jan, 2022
     * @param licenseNocPaymentSettingRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Save License Noc Payment Setting", description = "Save License Noc Payment Setting in Database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Save License Noc Payment Setting in Database.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Payment Setting failed to save!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PostMapping(LicenseNocPaymentSettingConstants.SAVE)
    public ResponseEntity<?> saveLicenseNocPaymentSetting(@RequestBody LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) {
    try {
        LicenseNocPaymentSetting savedLicenseNocPaymentSetting = iLicenseNocPaymentSettingService.saveLicenseNocPaymentSetting(licenseNocPaymentSettingRequest);
        if(ObjectUtils.isEmpty(savedLicenseNocPaymentSetting)) {
            return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.NOT_SAVED_EN, LicenseNocPaymentMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
        }
        LOG.info("License Noc Payment Setting saved");
        return ResponseEntity.ok(new ApiResponse(true, LicenseNocPaymentMessageConstants.SAVED_EN, LicenseNocPaymentMessageConstants.SAVED_BN, savedLicenseNocPaymentSetting, 1L));
    } catch (EntityExistsException e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.ALREADY_EXIST_EN, LicenseNocPaymentMessageConstants.ALREADY_EXIST_BN, null), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    /**
     * This api is to update License Noc Payment Setting in Database
     * @author Md Mushfiq Fuad
     * @since 30 Jan, 2022
     * @param licenseNocPaymentSettingRequest - payload
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Update License Noc Payment Setting", description = "Update License Noc Payment Setting in Database")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Update License Noc Payment Setting in Database.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Payment Setting failed to update!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @PutMapping(LicenseNocPaymentSettingConstants.UPDATE)
    public ResponseEntity<?> updateLicenseNocPaymentSetting(@RequestBody LicenseNocPaymentSettingRequest licenseNocPaymentSettingRequest) {
    try {
         LicenseNocPaymentSetting updatedLicenseNocPaymentSetting = iLicenseNocPaymentSettingService.updateLicenseNocPaymentSetting(licenseNocPaymentSettingRequest);
         if(ObjectUtils.isEmpty(updatedLicenseNocPaymentSetting)) {
         return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.NOT_UPDATED_EN, LicenseNocPaymentMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
         }
         LOG.info("License Noc Payment Setting updated");
         return ResponseEntity.ok(new ApiResponse(true, LicenseNocPaymentMessageConstants.UPDATED_EN, LicenseNocPaymentMessageConstants.UPDATED_BN, updatedLicenseNocPaymentSetting, 1L));
    } catch (Exception e) {
        LOG.info(e.getMessage());
        return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    
    /**
     * This api is to get Noc Payment Settings by category type and category id
     * 
     * @author Md Emran Hossain
     * @param type - String
     * @param categoryId - Long
     * @since 03 Apr, 2022
     * @return ResponseEntity - ResponseEntity
     */
    @Operation(summary = "Get Noc Payment Settings", description = "Get Noc Payment Settings")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Get Noc Payment Settings.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocPaymentSettingController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Noc Payment Settings failed to retrieve!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @GetMapping(LicenseNocPaymentSettingConstants.HAS_LICENSE_NOC_PAYMENT_SETTING)
    public ResponseEntity<?> hasPaymentSetting(@RequestParam String type, @RequestParam Long categoryId) {
        try {
            LicenseNocPaymentSetting paymentSetting = iLicenseNocPaymentSettingService.hasPaymentSetting(type, categoryId);
            if(ObjectUtils.isEmpty(paymentSetting)) {
                return new ResponseEntity(new ApiResponse(false, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_EN, LicenseNocPaymentMessageConstants.LICENSE_NOC_PAYMENT_NOT_FOUND_BN, null ,null), HttpStatus.OK);
            }
            LOG.info("All Noc Payment Setting retrieved");
            return new ResponseEntity(new ApiResponse(true, LicenseNocPaymentMessageConstants.ALREADY_EXIST_EN, LicenseNocPaymentMessageConstants.ALREADY_EXIST_BN, paymentSetting, 1L), HttpStatus.OK);
        } catch(Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_EN, LicenseNocPaymentMessageConstants.SOMTHING_WRONG_BN, null), HttpStatus.OK);
        }
    }
}
