package com.synesis.mofl.lnm.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synesis.mofl.lnm.exception.ErrorResponse;
import com.synesis.mofl.lnm.helper.constants.LicenseNocStatusConstants;
import com.synesis.mofl.lnm.helper.message.LicenseNocStatusMessageConstants;
import com.synesis.mofl.lnm.model.LicenseNocStatus;
import com.synesis.mofl.lnm.payload.ApiResponse;
import com.synesis.mofl.lnm.service.IService.ILicenseNocStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * This controller is to provide license noc status api
 *
 * @author Md. Emran Hossain
 * @since 23 Mar, 2022
 * @version 1.1
 */
@RestController
@RequestMapping(LicenseNocStatusConstants.ROOT)
public class LicenseNocStatusController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseNocStatusController.class);

    @Autowired
    ILicenseNocStatusService iLicenseNocStatusService;

    /**
     * This api is for get all License Noc Statuses
     *
     * @author Md. Emran Hossain
     * @param  pageable - Pageable
     * @return ResponseEntity - ResponseEntity
     * @since 24 Jan, 2022
     */
    @Operation(summary = "License Noc Statuses", description = "All License Noc Statuses")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All License Noc Statuses Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocStatusController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Statuses Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseNocStatusConstants.GET_ALL)
    public ResponseEntity<?> getAllLicenseNocStatus(Pageable pageable) {
        try {
            Page<LicenseNocStatus> allLicenseNocStatus = iLicenseNocStatusService.getAllLicenseNocStatus(pageable);
            if (allLicenseNocStatus.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_NOT_FOUND_EN, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            Long element = allLicenseNocStatus.getTotalElements();
            return ResponseEntity.ok(new ApiResponse(true, LicenseNocStatusMessageConstants.ALL_LICENSE_NOC_STATUS_EN, LicenseNocStatusMessageConstants.ALL_LICENSE_NOC_STATUS_BN, allLicenseNocStatus.getContent(), element));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseNocStatusMessageConstants.SOMTHING_WRONG_EN, LicenseNocStatusMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is for get License Noc Stauts by id
     *
     * @author Md. Emran Hossain
     * @param  id - Long
     * @return ResponseEntity - ResponseEntity
     * @since 23 Mar, 2022
     */
    @Operation(summary = "License Noc Status", description = "License Noc Status By Id")
    @ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "License Noc Status Found.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LicenseNocStatusController.class)))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "License Noc Status Not Found!",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
            })
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(LicenseNocStatusConstants.GET_BY_ID)
    public ResponseEntity<?> getLicenseNocStatusById(@PathVariable Long id) {
        try {
            LicenseNocStatus licenseNocStatus = iLicenseNocStatusService.getLicenseNocStatusById(id);
            if (ObjectUtils.isEmpty(licenseNocStatus)) {
                return new ResponseEntity(new ApiResponse(false, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_NOT_FOUND_EN, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_NOT_FOUND_BN, null, null), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse(true, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_EN, LicenseNocStatusMessageConstants.LICENSE_NOC_STATUS_BN, licenseNocStatus, 1L));
        }catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity(new ErrorResponse(false, LicenseNocStatusMessageConstants.SOMTHING_WRONG_EN, LicenseNocStatusMessageConstants.SOMTHING_WRONG_BN, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
