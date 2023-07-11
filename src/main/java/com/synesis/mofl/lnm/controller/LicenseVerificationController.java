package com.synesis.mofl.lnm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.synesis.mofl.lnm.helper.constants.LicenseVerificationConstants;
import com.synesis.mofl.lnm.helper.message.LicenseVerificationMessageConstants;
import com.synesis.mofl.lnm.model.LicenseVerification;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.payload.LicenseVerificationPayload;
import com.synesis.mofl.lnm.payload.LicenseVerificationResponse;
import com.synesis.mofl.lnm.service.IService.ILicenseVerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide license verification api
 *
 * @author Md. Emran Hossain
 * @since 02 Feb, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(LicenseVerificationConstants.ROOT)
public class LicenseVerificationController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseVerificationController.class);

    @Autowired
    ILicenseVerificationService iLicenseVerificationService;

    /**
     * This api is for get License Verification by id
     *
     * @author Md. Emran Hossain
     * @author Md Mushfiq Fuad
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Feb, 2022
     */
    @Operation(summary = "Get Verification By Id", description = "License Verification By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Verification Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Verification Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseVerificationConstants.GET_BY_ID)
    public ResponseEntity<?> getLicenseVerificationById(@PathVariable Long id) {
        try {
            LicenseVerificationResponse licenseVerification = iLicenseVerificationService.getLicenseVerificationById(id);
            if (ObjectUtils.isEmpty(licenseVerification)) {
                return new ResponseEntity(new ApiResponse(false, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_EN, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_EN, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_BN, licenseVerification, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseVerificationMessageConstants.SOMTHING_WRONG_EN, LicenseVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Verification by Application id
     *
     * @author Md. Emran Hossain
     * @param  appId - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Feb, 2022
     */
    @Operation(summary = "Get Verification By Application Id", description = "License Verification By Application Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Verification Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Verification Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseVerificationConstants.GET_BY_APP_ID)
    public ResponseEntity<?> getLicenseVerificationByAppId(@RequestParam("applicationId") Long appId) {
        try {
            LicenseVerification licenseVerification = iLicenseVerificationService.getLicenseVerificationByappId(appId);
            if (ObjectUtils.isEmpty(licenseVerification)) {
                return new ResponseEntity(new ApiResponse(false, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_EN, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseVerificationMessageConstants.ALL_LICENSE_VERIFICATION_EN, LicenseVerificationMessageConstants.ALL_LICENSE_VERIFICATION_BN, licenseVerification, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseVerificationMessageConstants.SOMTHING_WRONG_EN, LicenseVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Verification by verified person id
     *
     * @author Md. Emran Hossain
     * @param  verifiedBy - Long
     * @return ResponseEntity - ResponseEntity
     * @since 02 Feb, 2022
     */
    @Operation(summary = "Get Verification For Verified by", description = "License Verification By verified person id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Verification Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Verification Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseVerificationConstants.GET_BY_USER_ID)
    public ResponseEntity<?> getLicenseVerificationByUserId(@RequestParam("userId") Long verifiedBy) {
        try {
            LicenseVerification licenseVerification = iLicenseVerificationService.getLicenseVerificationByUserId(verifiedBy);
            if (ObjectUtils.isEmpty(licenseVerification)) {
                return new ResponseEntity(new ApiResponse(false, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_EN, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_NOT_FOUND_BN,null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_EN, LicenseVerificationMessageConstants.LICENSE_VERIFICATION_BN, licenseVerification, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseVerificationMessageConstants.SOMTHING_WRONG_EN, LicenseVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Save License Verification
     *
     * @author Md. Emran Hossain
     * @param licenseVerificationPayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 02 Feb, 2022
     */
    @Operation(summary = "Save License Verification", description = "Save License Verification")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Verification Saved.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Verification Not Saved!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(LicenseVerificationConstants.SAVE)
    public ResponseEntity<?> saveLicenseVerification(@RequestBody LicenseVerificationPayload licenseVerificationPayload) {
        try {
            LicenseVerification saveLicenseVerification = iLicenseVerificationService.saveLicenseVerification(licenseVerificationPayload);
            if (ObjectUtils.isEmpty(saveLicenseVerification)) {
                return new ResponseEntity(new ApiResponse(false, LicenseVerificationMessageConstants.NOT_SAVED_EN, LicenseVerificationMessageConstants.NOT_SAVED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseVerificationMessageConstants.SAVED_EN, LicenseVerificationMessageConstants.SAVED_BN, saveLicenseVerification, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseVerificationMessageConstants.SOMTHING_WRONG_EN, LicenseVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for Update License Verification
     *
     * @author Md. Emran Hossain
     * @param licenseVerificationPayload - Payload
     * @return ResponseEntity - ResponseEntity
     * @since 02 Feb, 2022
     */
    @Operation(summary = "Update License Verification", description = "Update License Verification")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Verification Updated.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseVerificationController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Verification Not Updated!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping(LicenseVerificationConstants.UPDATE)
    public ResponseEntity<?> updateLicenseVerification(@RequestBody LicenseVerificationPayload licenseVerificationPayload) {
        try {
            LicenseVerification updateLicenseVerification = iLicenseVerificationService.updateLicenseVerification(licenseVerificationPayload);
            if (ObjectUtils.isEmpty(updateLicenseVerification)) {
                return new ResponseEntity(new ApiResponse(false, LicenseVerificationMessageConstants.NOT_UPDATED_EN, LicenseVerificationMessageConstants.NOT_UPDATED_BN, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseVerificationMessageConstants.UPDATED_EN, LicenseVerificationMessageConstants.UPDATED_BN, updateLicenseVerification, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseVerificationMessageConstants.SOMTHING_WRONG_EN, LicenseVerificationMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
